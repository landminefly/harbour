package com.admin.analysis;

import com.api.CargoFlowRatioServer;
import com.api.viewBean.CargoFlowRatioBean;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet("/servlet/analysis/m4")
public class CargoFlowRatio extends HttpServlet {
    /**
     * 处理不同货物流向分析图表的请求
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
        String arg = sb.toString();
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/json");
        PrintWriter writer = res.getWriter();
        CargoFlowRatioServer cargoFlowRatioServer = new CargoFlowRatioServer();
        if ("queryForCargoTypes".equals(arg)) {
            List<String> types = cargoFlowRatioServer.queryForCargoTypes((Connection) req.getAttribute("conn"));
            writer.write(new Gson().toJson(types, List.class));
        } else {
            List<CargoFlowRatioBean> items = cargoFlowRatioServer.query((Connection) req.getAttribute("conn"), arg);
            writer.write(new Gson().toJson(items, List.class));
        }
    }
}
