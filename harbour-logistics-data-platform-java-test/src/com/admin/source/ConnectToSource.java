package com.admin.source;

import com.Utils.JdbcUtils;
import com.api.ConnectToHadoop;
import com.api.ConnectToMinIO;
import com.api.ConnectToMySQL;
import com.api.SourcesServer;
import com.google.gson.Gson;
import com.mvc.bean.SourcesBean;
import org.checkerframework.checker.units.qual.C;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.sql.Connection;
import java.util.HashMap;

@WebServlet("/servlet/admin/connect-source")
public class ConnectToSource extends HttpServlet {

    /**
     * 处理新增数据源请求
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            sb.append(temp);
        }
        Gson gson = new Gson();
        String[] source = gson.fromJson(sb.toString(), String[].class);
        SourcesServer sourcesServer = new SourcesServer();
        Connection conn = (Connection) req.getAttribute("conn");
        if(source[0].contains("MySQL")){

            String url = "jdbc:mysql://" + source[1] + ":3306/" + source[2] + "?useSSL=false&serverTimezone=UTC";
            if(sourcesServer.isExist(conn, new SourcesBean(url))){
                throw new RuntimeException("数据源已存在");
            }


            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("url",url);
            hashMap.put("username",source[3]);
            hashMap.put("password",source[4]);
            hashMap.put("driverClassName","com.mysql.cj.jdbc.Driver");

            new ConnectToMySQL().connect(conn,hashMap);

            sourcesServer.insert(conn,new SourcesBean(source[0],source[1],source[3],source[4]));

        }else if(source[0].contains("HDFS")){
            String url = source[1];
            if(sourcesServer.isExist(conn, new SourcesBean(url))){
                throw new RuntimeException("数据源已存在");
            }

            new ConnectToHadoop().connect(conn,url);

            sourcesServer.insert(conn,new SourcesBean(source[0],url,null,null));
        }else if(source[0].contains("MinIO")){
            String url = source[1];
            if(sourcesServer.isExist(conn, new SourcesBean(url))){
                throw new RuntimeException("数据源已存在");
            }

            new ConnectToMinIO().connect(conn,url,source[2],source[3]);

            sourcesServer.insert(conn,new SourcesBean(source[0],url,source[2],source[3]));
        }
    }
}
