package com.parser.fileSpecificParserImpl;

import com.parser.FileSpecificParser;
import com.parser.enums.TableEnum;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

/**
 * 解析Xlsx的类，使用了Poi库来读取文件
 */
public class FileXlsxParser implements FileSpecificParser {
    //表枚举属性
    TableEnum tableEnum = null;

    @Override
    public void parse(File file, Connection conn) throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvalidFormatException, InvocationTargetException {
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

        XSSFWorkbook sheets = null;
        FileInputStream fileInputStream = null;
        try {
            //暂存多个bean，以便统一传入insertBatch方法
            ArrayList<Object> beans = new ArrayList<>();
            //记录当前beans的长度
            int beansLength = 0;
            //获取sheet
            fileInputStream = new FileInputStream(file);
            sheets = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = sheets.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //获取行
                XSSFRow row = sheet.getRow(i);
                //新建一个bean对象
                bean = beanClassConstructorconstructor.newInstance();
                //将各个单元格赋值给bean中的各个属性
                int beanIndex = 0;
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    //如果遇到自增列，则直接跳过
                    if ("id".equals(fields[beanIndex].getName())) {
                        beanIndex++;
                        j--;
                        continue;
                    }
                    //获取单元格数据
                    XSSFCell cell = row.getCell(j);
                    String stringCellValue = cell.getStringCellValue();
                    //获取当前属性的类型
                    Class<?> type = fields[beanIndex].getType();
                    fields[beanIndex].setAccessible(true);
                    //如果是非String，则需要进行数据转换
                    if (type == Timestamp.class) {
                        DateTimeFormatter formatter = null;
                        LocalDateTime parse = null;
                        if (stringCellValue.contains(" ")) {
                            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            parse = LocalDateTime.parse(stringCellValue, formatter);
                        } else {
                            //这是只有年月日，没有时分秒的情况，需要先格式化为LocalDate，然后再转化为LocalDateTime
                            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            parse = LocalDate.parse(stringCellValue, formatter).atStartOfDay();
                        }
                        Timestamp timestamp = Timestamp.valueOf(parse);
                        fields[beanIndex].set(bean, timestamp);
                    } else if (type == Date.class) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate parse = LocalDate.parse(stringCellValue, formatter);
                        Date date = Date.valueOf(parse);
                        fields[beanIndex].set(bean, date);
                    } else if (type == int.class) {
                        int in = Integer.parseInt(stringCellValue);
                        fields[beanIndex].set(bean, in);
                    }
                    //如果是String，直接赋值即可
                    else {
                        fields[beanIndex].set(bean, stringCellValue);
                    }
                    beanIndex++;
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
            //最后还要调用一次insertBatch方法
            insertBatch.invoke(DAO, conn, beans);
            beans.clear();
            beansLength = 0;

        } catch (InvocationTargetException e){
            System.out.println("插入失败,有重复记录");
        } catch(IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (sheets != null) {
                sheets.close();
            }
        }
    }
}
