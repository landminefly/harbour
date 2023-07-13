package com.parser;
import com.parser.enums.FileEnum;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 泛的解析类，判断文件的具体类型后转到对应的具体解析类执行
 * 外部可以直接调用该类完成文件的解析操作
 */
public class FileParser {
    FileEnum fileEnum = null;

    public void parse(File file, Connection conn) throws IOException, SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvalidFormatException {
        String name = file.getName();
        if(name.matches(".*\\.txt$")){
            fileEnum = FileEnum.TXT;
        }else if(name.matches(".*\\.xlsx$")){
            fileEnum = FileEnum.XLSX;
        }else if(name.matches(".*\\.csv$")){
            fileEnum = FileEnum.CSV;
        }else{
            throw new RuntimeException("不支持该格式文件的解析！");
        }

        this.fileEnum.getSpecificParser().parse(file, conn);
    }

}
