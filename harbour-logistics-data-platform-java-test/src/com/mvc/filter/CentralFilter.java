package com.mvc.filter;

import com.Utils.JdbcUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(urlPatterns = "/*")
public class CentralFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Connection conn = null;
        try {
            //获取连接，设置自动提交为false，并存储到request中
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            request.setAttribute("conn", conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            //放行
            filterChain.doFilter(request, response);
            //提交
            conn.commit();
        } catch (IOException | ServletException | SQLException e) {

            //如果出现异常，则回滚
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } finally {

            //关闭连接
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
