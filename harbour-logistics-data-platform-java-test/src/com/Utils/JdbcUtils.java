package com.Utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    //连接池对象
    private static DataSource dataSource = null;

    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    static{
        //初始化连接池对象
        Properties properties = new Properties();
        InputStream ips = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(ips);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //对外提供连接
    public static Connection getConnection() throws SQLException {
        //线程本地变量中是否存在
        Connection connection = tl.get();
        //第一次没有
        if (connection == null) {
            //线程本地变量没有,连接池获取
            connection = dataSource.getConnection();
            tl.set(connection);
        }
        return  connection;
    }

    //回收连接
    public static void freeConnection() throws SQLException {
        Connection connection = tl.get();
        if (connection != null) {
            tl.remove(); //清空线程本地变量数据
            connection.setAutoCommit(true); //事务状态回滚 false
            connection.close(); //回收到连接池即可
        }
    }
}