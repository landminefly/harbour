package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.LogisticsCompanyDAO;
import com.mvc.bean.CustomerBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LogisticsCompanyDAOImpl extends BaseDAO<CustomerBean> implements LogisticsCompanyDAO {
    @Override
    public int update(Connection conn, Object... args) throws SQLException {
        String sql = "";
        return super.update(conn,sql,args);
    }

    @Override
    public CustomerBean selectForOne(Connection conn, Object... args) throws SQLException {
        String sql = "";
        return super.selectForOne(conn,sql,args);
    }

    @Override
    public List<CustomerBean> selectForMulti(Connection conn, Object... args) throws SQLException {
        String sql = "";
        return super.selectForMulti(conn,sql,args);
    }

    @Override
    public <E> E getValue(Connection conn, Object... args) throws SQLException {
        String sql = "";
        return super.getValue(conn,sql,args);
    }
}
