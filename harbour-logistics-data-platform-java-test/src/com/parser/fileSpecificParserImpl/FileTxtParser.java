package com.parser.fileSpecificParserImpl;

import com.parser.FileSpecificParser;
import com.parser.enums.TableEnum;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTxtParser implements FileSpecificParser {
    //表枚举属性
    TableEnum tableEnum = null;
    //正则规则
    Pattern compile = Pattern.compile("(?<=^|',)(.*?)(?=,'|,\\d|$)");

    @Override
    public void parse(File file, Connection conn) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        //判断文件数据属于哪个表，并更新表枚举属性
        String name = file.getName();
        if (name.contains("装货表")) {
            tableEnum = TableEnum.LOAD;
        } else if (name.contains("卸货表")) {
            tableEnum = TableEnum.UNLOAD;
        } else if (name.contains("物流信息")) {
            tableEnum = TableEnum.LOGISTICS_INFO;
        } else if (name.contains("物流公司")) {
            tableEnum = TableEnum.LOGISTICS_COMPANY;
        } else if (name.contains("集装箱动态")) {
            tableEnum = TableEnum.CONTAINER_STATUS;
        }

        //根据表枚举属性获取到对应的javaBean类及其属性
        Class<?> beanClass = tableEnum.getBeanClass();
        Constructor<?> beanClassConstructorconstructor = beanClass.getConstructor();
        //需要多个javaBean，此处先赋值为null
        Object bean = null;
        Field[] fields = beanClass.getDeclaredFields();

        //根据表枚举属性获取到对应的DAO类及其insertBatch方法
        Class<?> DAOClass = tableEnum.getDAOImplClass();
        Constructor<?> DAOConstructor = DAOClass.getConstructor();
        //只需一个DAO对象
        Object DAO = DAOConstructor.newInstance();
        Method insertBatch = DAOClass.getDeclaredMethod("insertBatch", Connection.class, ArrayList.class);

        //获取缓冲字节输入流，注意编码为GBK
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        //暂存多个bean，以便统一传入insertBatch方法
        ArrayList<Object> beans = new ArrayList<>();
        //一次读一行，str表示当前读到的内容
        String str = null;
        //略过第一行
        str = bufferedReader.readLine();
        //记录当前beans的长度
        int beansLength = 0;
        while ((str = bufferedReader.readLine()) != null) {
            //对str进行正则匹配
            Matcher matcher = this.compile.matcher(str);
            //新建一个bean对象
            bean = beanClassConstructorconstructor.newInstance();

            //将正则匹配结果赋值给bean中的各个属性
            int index = 0;
            //先转到第一个匹配结果
            matcher.find();
            while (true) {
                //将当前匹配结果赋值给split
                String split = matcher.group(1);
                //如果含有单引号，则去掉
                if(split.contains("'")){
                    split = split.replace("'","");
                }
                //如果遇到自增列，则直接跳过
                if ("id".equals(fields[index].getName())) {
                    index++;
                    continue;
                }
                //获取当前属性的类型
                Class<?> type = fields[index].getType();
                fields[index].setAccessible(true);
                //如果是非String，则需要进行数据转换
                if (type == Timestamp.class) {
                    DateTimeFormatter formatter = null;
                    LocalDateTime parse = null;
                    if (split.contains(" ")) {
                        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        parse = LocalDateTime.parse(split, formatter);
                    } else {
                        //这是只有年月日，没有时分秒的情况，需要先格式化为LocalDate，然后再转化为LocalDateTime
                        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        parse = LocalDate.parse(split, formatter).atStartOfDay();
                    }
                    Timestamp timestamp = Timestamp.valueOf(parse);
                    fields[index].set(bean, timestamp);
                } else if (type == Date.class) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate parse = LocalDate.parse(split, formatter);
                    Date date = Date.valueOf(parse);
                    fields[index].set(bean, date);
                } else if (type == int.class) {
                    int i = Integer.parseInt(split);
                    fields[index].set(bean, i);
                }
                //如果是String，直接赋值即可
                else {
                    fields[index].set(bean, split);
                }

                index++;
                //如果所有属性均被赋值，则退出循环
                if (!matcher.find()) {
                    break;
                }
            }

            //将赋值好的bean加入beans中
            beans.add(bean);
            beansLength++;

            //达到一定长度时，将beans传入insertBatch方法，然后清空beans
            if (beansLength == 1024) {
                insertBatch.invoke(DAO, conn, beans);
                beans.clear();
                beansLength = 0;
            }
        }
    }
}
