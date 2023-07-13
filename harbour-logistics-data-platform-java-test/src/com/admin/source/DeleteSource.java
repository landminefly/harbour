package com.admin.source;

import com.api.SourcesServer;
import com.google.gson.Gson;
import com.mvc.DAO.impl.CustomerDAOImpl;
import com.mvc.bean.SourcesBean;
import com.parser.enums.TableEnum;
import org.checkerframework.checker.units.qual.C;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/servlet/admin/delete-source")
public class DeleteSource extends HttpServlet {
    TableEnum tableEnum = null;

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            //接收数据，并转换为bean
            req.setCharacterEncoding("utf-8");
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
            String str = sb.toString();
            String[] strs = new Gson().fromJson(str, String[].class);
            String strType = strs[0];
            String address = strs[1];

            SourcesServer sourcesServer = new SourcesServer();
            Connection conn = (Connection) req.getAttribute("conn");

            if (strType.contains("HDFS")) {
                String localPath = "/harbour/hadoopFiles";
                File file = new File(localPath);
                for (File listFile : file.listFiles()) {
                    //判断文件数据属于哪个表，并更新表枚举属性
                    String name = listFile.getName();
                    if (name.contains("装货表")) {
                        tableEnum = TableEnum.LOAD;
                    } else if (name.contains("卸货表")) {
                        tableEnum = TableEnum.UNLOAD;
                    } else if (name.contains("物流信息")) {
                        tableEnum = TableEnum.LOGISTICS_INFO;
                    } else if (name.contains("物流公司")) {
                        tableEnum = TableEnum.LOGISTICS_COMPANY;
                    } else if (name.contains("集装箱动态")) {
                        tableEnum = TableEnum.CONTAINER_STATUS;
                    }

                    Class<?> DAOClass = tableEnum.getDAOImplClass();
                    Constructor<?> DAOConstructor = DAOClass.getConstructor();
                    Object DAO = DAOConstructor.newInstance();
                    Method insertBatch = DAOClass.getDeclaredMethod("truncate", Connection.class);
                    insertBatch.invoke(DAO, conn);
                    listFile.delete();
                }
            } else if (strType.contains("MinIO")) {
                String localPath = "/harbour/minIOFiles/";
                File file = new File(localPath);
                for (File listFile : file.listFiles()) {
                    //判断文件数据属于哪个表，并更新表枚举属性
                    String name = listFile.getName();
                    if (name.contains("装货表")) {
                        tableEnum = TableEnum.LOAD;
                    } else if (name.contains("卸货表")) {
                        tableEnum = TableEnum.UNLOAD;
                    } else if (name.contains("物流信息")) {
                        tableEnum = TableEnum.LOGISTICS_INFO;
                    } else if (name.contains("物流公司")) {
                        tableEnum = TableEnum.LOGISTICS_COMPANY;
                    } else if (name.contains("集装箱动态")) {
                        tableEnum = TableEnum.CONTAINER_STATUS;
                    }

                    Class<?> DAOClass = tableEnum.getDAOImplClass();
                    Constructor<?> DAOConstructor = DAOClass.getConstructor();
                    Object DAO = DAOConstructor.newInstance();
                    Method insertBatch = DAOClass.getDeclaredMethod("truncate", Connection.class);
                    insertBatch.invoke(DAO, conn);
                    listFile.delete();
                }
            } else if (strType.contains("MySQL")) {
                new CustomerDAOImpl().truncate(conn);
            }
            if (sourcesServer.isExist(conn, new SourcesBean(address))) {
                sourcesServer.delete(conn, new SourcesBean(address));
            } else {
                throw new RuntimeException("不存在此记录");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
