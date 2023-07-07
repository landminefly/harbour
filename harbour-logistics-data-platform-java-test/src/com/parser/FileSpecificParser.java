package com.parser;
import com.parser.enums.FileEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public interface FileSpecificParser {
    public void parse(File file, Connection conn) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, SQLException;

}
