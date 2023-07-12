package com.admin.analysis;

import com.api.TotalTrendServer;
import com.api.YardTimeServer;
import com.api.viewBean.TotalTrendBean;
import com.api.viewBean.YardTimeBean;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/servlet/analysis/m5")
public class YardTime extends HttpServlet {
    /**
     * 处理不同类型货物堆场流转周期分析图表的请求
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        YardTimeServer yardTimeServer = new YardTimeServer();
        List<YardTimeBean> results = yardTimeServer.query((Connection) req.getAttribute("conn"));
        String json = new Gson().toJson(results, List.class);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        // 将数组JSON字符串作为响应发送
        res.getWriter().write(json);
    }
}
