package com.mvc.listener;

import com.Utils.JdbcUtils;
import com.api.ConnectToHadoop;
import com.api.ConnectToMinIO;
import com.api.ConnectToMySQL;
import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.impl.SourcesDAOImpl;
import com.mvc.bean.SourcesBean;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

@WebListener
public class CentralListener implements ServletContextListener {

    /**
     * 注意：如果想要在本地服务器上运行，必须把这个方法注释掉，否则连接不上数据源将导致
     * contextInitialized方法无法执行完毕，也就会导致项目无法部署
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //1.导入MySQL数据源
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("url","jdbc:mysql://mysqla-mysqld.damenga-zone.svc:3306/cnsoft?useSSL=false&serverTimezone=UTC");
            hashMap.put("username","mysqluser");
            hashMap.put("password","Dameng123");
            hashMap.put("driverClassName","com.mysql.cj.jdbc.Driver");

            new ConnectToMySQL().connect(conn,hashMap);
            conn.commit();
        } catch (Exception e) {

            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(e.getMessage());

        }finally {

            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        //2.导入HDFS数据源
        conn = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            new ConnectToHadoop().connect(conn,"hadoopa-namenode.damenga-zone.svc:9000");
            conn.commit();
        } catch (Exception e) {

            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(e.getMessage());

        }finally {

            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        //3.导入MinIO数据源
        conn = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            new ConnectToMinIO().connect(conn,"http://minio.damenga-zone.svc/","cnsof24014586","Cnsoft18949405800");
            conn.commit();
        } catch (Exception e) {

            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(e.getMessage());

        }finally {

            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        //4.更新Sources表
        conn = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            SourcesDAOImpl sourcesDAO = new SourcesDAOImpl();
            //1.先清空
            sourcesDAO.truncate(conn);
            //2.加入MySQL数据源
            sourcesDAO.insert(conn,new SourcesBean("MySQL数据库","mysqla-mysqld.damenga-zone.svc","",null));
            //3.加入HDFS数据源
            sourcesDAO.insert(conn,new SourcesBean("HDFS分布式存储","hadoopa-namenode.damenga-zone.svc:9000",null,null));
            //4.加入MinIO数据源
            sourcesDAO.insert(conn,new SourcesBean("MinIO分布式存储","http://minio.damenga-zone.svc/","mysqluser","Dameng123"));
            //5.提交
            conn.commit();
        } catch (Exception e) {

            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(e.getMessage());

        }finally {

            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }
}
