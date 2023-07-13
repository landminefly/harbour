package com.admin.source;

import com.api.SourcesServer;
import com.api.viewBean.LogisticsCompanyViewBean;
import com.google.gson.Gson;
import com.mvc.bean.SourcesBean;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/servlet/admin/select-source")
public class SelectSource extends HttpServlet {

    /**
     * 处理查询数据源的请求
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        SourcesServer sourcesServer = new SourcesServer();
        List<SourcesBean> results = sourcesServer.selectForAll((Connection) req.getAttribute("conn"));
        //将得到的封装对象中的各个属性单独拿出来，按照前端页面的数据格式放入一个数组中
        //多个封装对象对应多个数组，最终得到一个二维数组
        String[][] resultsArr = new String[results.size()][];
        for(int i = 0; i<resultsArr.length; i++){
            SourcesBean result = results.get(i);
            String[] resultData = new String[2];
            resultData[0] = result.getSource_type();
            resultData[1] = result.getAddress();
            resultsArr[i] = resultData;
        }

        //格式化后响应给前端
        Gson gson = new Gson();
        String arrayJson = gson.toJson(resultsArr);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // 将数组JSON字符串作为响应发送
        res.getWriter().write(arrayJson);
    }
}
