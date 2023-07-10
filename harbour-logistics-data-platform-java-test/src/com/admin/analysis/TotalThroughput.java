package com.admin.analysis;

import com.api.TotalThroughputServer;
import com.api.viewBean.TotalThroughputBean;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/servlet/analysis/m0")
public class TotalThroughput extends HttpServlet {
    /**
     * 处理港口的吞吐量分析图表的请求
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        TotalThroughputServer totalThroughputServer = new TotalThroughputServer();
        List<TotalThroughputBean> results = totalThroughputServer.query((Connection) req.getAttribute("conn"));
        String json = new Gson().toJson(results, List.class);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        // 将数组JSON字符串作为响应发送
        res.getWriter().write(json);
    }
}
