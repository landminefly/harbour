package com.test;

import com.Utils.CUSTOMER_Bean;
import com.Utils.CUSTOMER_Dao;
import com.Utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test {

    @Test
    public void test() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        CUSTOMER_Dao CUSTOMERDao = new CUSTOMER_Dao();

        String sql = "DELETE FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        System.out.println(CUSTOMERDao.update(connection, sql, "ZYF"));

    }


    @Test
    public void test2() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        CUSTOMER_Dao CUSTOMERDao = new CUSTOMER_Dao();

        String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        CUSTOMER_Bean b = new CUSTOMER_Bean();
        b = CUSTOMERDao.selectForOneReturnsBean(connection,sql,"hehe");

    }

    @Test
    public void test3() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        CUSTOMER_Dao CUSTOMERDao = new CUSTOMER_Dao();

        String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        List<CUSTOMER_Bean> customerBeans = new ArrayList<>();
        customerBeans = CUSTOMERDao.selectNotForOneReturnsBeanList(connection,sql,"haha");

        System.out.println(customerBeans);

    }

    @Test
    public void test4() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        CUSTOMER_Dao CUSTOMERDao = new CUSTOMER_Dao();

        String sql = "SELECT count(*) FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        long i = CUSTOMERDao.getValue(connection,sql,"haha");
        System.out.println(i);

    }

    @Test
    public void test5() throws SQLException {

        Connection connection = JdbcUtils.getConnection();

        CUSTOMER_Dao CUSTOMERDao = new CUSTOMER_Dao();

        String sql = "SELECT CUSTOMER_NUMBER FROM CUSTOMER WHERE CUSTOMER_NAME = ?;";

        String i = CUSTOMERDao.getValue(connection,sql,"haha");
        System.out.println(i);

    }

}
