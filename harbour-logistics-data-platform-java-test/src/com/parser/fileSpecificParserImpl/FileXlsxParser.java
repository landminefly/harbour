package com.parser.fileSpecificParserImpl;

import com.parser.FileSpecificParser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class FileXlsxParser implements FileSpecificParser {
    @Override
    public void parse(File file, Connection conn) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, SQLException {

    }
}
