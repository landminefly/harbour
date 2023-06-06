package com.Utils;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test {

    @Test
    public void test() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        Dao dao = new Dao();

        String sql = "DELETE FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        System.out.println(dao.update(connection, sql, "ZYF"));

    }


    @Test
    public void test2() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        Dao dao = new Dao();

        String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        CUSTOMER_Bean b = new CUSTOMER_Bean();
        b = dao.selectForOneReturnsBean(connection,sql,"hehe");

    }

    @Test
    public void test3() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        Dao dao = new Dao();

        String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        List<CUSTOMER_Bean> customerBeans = new ArrayList<>();
        customerBeans = dao.selectNotForOneReturnsBeanList(connection,sql,"haha");

        System.out.println(customerBeans);

    }

    @Test
    public void test4() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        Dao dao = new Dao();

        String sql = "SELECT count(*) FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        long i = dao.getValue(connection,sql,"haha");
        System.out.println(i);

    }

    @Test
    public void test5() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        Dao dao = new Dao();

        String sql = "SELECT CUSTOMER_NUMBER FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        String i = dao.getValue(connection,sql,"haha");
        System.out.println(i);

    }

}
