package com.admin.login;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/servlet/admin/login")
public class AdminLogin extends HttpServlet {
    /**
     * 处理管理员登录请求
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            sb.append(temp);
        }
        AdminLoginServletBean info = new Gson().fromJson(sb.toString(), AdminLoginServletBean.class);

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain;charset=utf8");
        if (
                "diana".equals(info.getUsername()) &&
                "12345".equals(info.getPwd())
        ) {
            resp.getWriter().write("success");
        }else{
            resp.getWriter().write("failure");
        }
    }
}
