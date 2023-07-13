package com.parser;
import com.parser.enums.FileEnum;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 具体到各个文件类型的文件解析器
 * 实现该接口的类全部采用单例模式，其唯一对象存储在FileEnum的对应枚举对象中
 */
public interface FileSpecificParser {
    public void parse(File file, Connection conn) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, SQLException, InvalidFormatException;

}
