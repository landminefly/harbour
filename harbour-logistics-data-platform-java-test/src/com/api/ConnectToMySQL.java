package com.api;

import com.Utils.JdbcUtils;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mvc.DAO.impl.CustomerDAOImpl;
import com.mvc.bean.CustomerBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConnectToMySQL {
    private DruidDataSource dataSource = null;
    private QueryRunner queryRunner = null;

    public void connect(Connection connForDM, Map<String,String> hashMap) {
        Connection connForMySQL = null;
        try {
            //使用连接池工厂DruidDataSourceFactory传入配置信息，创建连接池dataSource
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(hashMap);
            //设置validationQuery
            dataSource.setValidationQuery("SELECT 1");
            //设置maxWait，防止数据库连接不上导致的程序假死
            dataSource.setMaxWait(3000);
            connForMySQL = dataSource.getConnection();
            queryRunner = new QueryRunner();

            CustomerDAOImpl customerDAO = new CustomerDAOImpl();
            customerDAO.truncate(connForDM);

            List<Object> query = queryRunner.query(connForMySQL, """
                                                                 SELECT 客户名称 AS NAME, 客户编号 AS CODE, 手机号 AS PHONE,省市区 AS ADDRESS FROM users2021
                                                                 """, new BeanListHandler<>(CustomerBean.class));
            customerDAO.insertBatchForDistinct(connForDM, query);

            query = queryRunner.query(connForMySQL, """
                                                    SELECT 客户名称 AS NAME, 客户编号 AS CODE, 手机号 AS PHONE,省市区 AS ADDRESS FROM users2022
                                                    """, new BeanListHandler<>(CustomerBean.class));
            customerDAO.insertBatchForDistinct(connForDM, query);

            customerDAO.distinct(connForDM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
