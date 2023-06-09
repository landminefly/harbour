package com.mvc.filter;

import com.Utils.JdbcUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(urlPatterns = "/*")
public class CentralFilter implements Filter {

    /**
     * 在执行所有请求所对应的Servlet之前，先获取一个DM数据库连接，设置为自动提交为false，并存储到request对象中
     * 此次请求所执行的所有数据库操作均使用该连接，以便出现问题后可以顺利将此次请求所执行的所有数据库操作回滚
     * 从而实现请求操作的原子化
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
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
