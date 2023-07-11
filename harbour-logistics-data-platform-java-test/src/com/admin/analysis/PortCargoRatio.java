package com.admin.analysis;

import com.api.PortCargoRatioServer;
import com.api.TotalTrendServer;
import com.api.viewBean.PortCargoRatioBean;
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

@WebServlet("/servlet/analysis/m3")
public class PortCargoRatio extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        PortCargoRatioServer portCargoRatioServer = new PortCargoRatioServer();
        List<PortCargoRatioBean> results = portCargoRatioServer.query((Connection) req.getAttribute("conn"));
        String json = new Gson().toJson(results, List.class);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        // 将数组JSON字符串作为响应发送
        res.getWriter().write(json);
    }
}
