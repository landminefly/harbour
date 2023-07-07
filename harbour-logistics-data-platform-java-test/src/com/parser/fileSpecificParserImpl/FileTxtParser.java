package com.parser.fileSpecificParserImpl;

import com.mvc.DAO.BaseDAO;
import com.parser.FileSpecificParser;
import com.parser.enums.TableEnum;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTxtParser implements FileSpecificParser {
    //表枚举属性
    TableEnum tableEnum = null;
    //正则规则
    Pattern compile = Pattern.compile("'(.*?)'");

    @Override
    public void parse(File file,Connection conn) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        //判断文件数据属于哪个表，并更新表枚举属性
        String name = file.getName();
        if(name.contains("装货表")){
            tableEnum = TableEnum.LOAD;
        }else if(name.contains("卸货表")){
            tableEnum = TableEnum.UNLOAD;
        }else if(name.contains("物流信息")){
            tableEnum = TableEnum.LOGISTICS_INFO;
        }else if(name.contains("物流公司")){
            tableEnum = TableEnum.LOGISTICS_COMPANY;
        }else if(name.contains("集装箱动态")){
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

        //获取缓冲字节输入流，注意编码为GB2312
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
        //暂存多个bean，以便统一传入insertBatch方法
        ArrayList<Object> beans = new ArrayList<>();
        //一次读一行，str表示当前读到的内容
        String str = null;
        //略过第一行
        str = bufferedReader.readLine();
        //
        int beansLength = 0;
        while((str = bufferedReader.readLine()) != null) {
            Matcher matcher = this.compile.matcher(str);

            bean = beanClassConstructorconstructor.newInstance();
            int count = 0;
            matcher.find();
            while (true) {
                if ("id".equals(fields[count].getName())) {
                    count++;
                    continue;
                }
                Class<?> type = fields[count].getType();
                if (type == Timestamp.class) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    TemporalAccessor parse = dateTimeFormatter.parse(matcher.group(1));
                    LocalDateTime from = LocalDateTime.from(parse);
                    Timestamp timestamp = Timestamp.valueOf(from);
                    fields[count].setAccessible(true);
                    fields[count].set(bean, timestamp);
                } else {
                    fields[count].setAccessible(true);
                    fields[count].set(bean, matcher.group(1));
                }
                count++;
                if (!matcher.find()) {
                    break;
                }
            }
            beans.add(bean);
            beansLength++;
            if(beansLength == 1000){
                insertBatch.invoke(DAO,conn,beans);
                beans.clear();
                beansLength = 0;
            }
        }

    }
}
