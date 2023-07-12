package com.mvc.listener;

import com.Utils.JdbcUtils;
import com.api.ConnectToHadoop;
import com.api.ConnectToMinIO;
import com.api.ConnectToMySQL;
import com.mvc.DAO.BaseDAO;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class CentralListener implements ServletContextListener {

    /**
     * 注意：如果想要在本地服务器上运行，必须把这个方法注释掉，否则连接不上数据源将导致
     * contextInitialized方法无法执行完毕，也就会导致项目无法部署
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Connection conn = null;
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


        conn = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            new ConnectToMySQL().connect(conn);
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

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(222);
    }
}
