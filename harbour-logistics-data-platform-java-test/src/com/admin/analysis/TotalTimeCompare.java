package com.admin.analysis;

import com.api.TotalTimeCompareServer;
import com.api.TotalTrendServer;
import com.api.viewBean.TotalTimeCompareBean;
import com.api.viewBean.TotalTrendBean;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/servlet/analysis/m2")
public class TotalTimeCompare extends HttpServlet {
    /**
     * 处理港口货物吞吐同比环比图表的请求
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        TotalTimeCompareServer totalTimeCompareServer = new TotalTimeCompareServer();
        List<TotalTimeCompareBean> results = totalTimeCompareServer.query((Connection) req.getAttribute("conn"));
        String json = new Gson().toJson(results, List.class);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        // 将数组JSON字符串作为响应发送
        res.getWriter().write(json);
    }
}
