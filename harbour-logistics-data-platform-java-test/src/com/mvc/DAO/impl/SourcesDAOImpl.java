package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.SourcesDAO;
import com.mvc.bean.SourcesBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SourcesDAOImpl extends BaseDAO<SourcesBean> implements SourcesDAO {
    @Override
    public int insert(Connection conn, SourcesBean bean) throws SQLException {
        String sql = "INSERT INTO SOURCES VALUES(?,?,?,?);";
        return super.update(conn,sql,bean.getSource_type(),bean.getAddress(),bean.getUsr(),bean.getPwd());
    }

    @Override
    public List<SourcesBean> selectForAll(Connection conn) throws SQLException {
        String sql = "SELECT SOURCE_TYPE,ADDRESS FROM SOURCES;";
        return super.selectForMulti(conn,sql);
    }

    @Override
    public boolean isExist(Connection conn, SourcesBean bean) throws SQLException {
        String sql = "SELECT SOURCE_TYPE,ADDRESS FROM SOURCES WHERE ADDRESS = ?;";
        List<SourcesBean> sourcesBeans = super.selectForMulti(conn, sql, bean.getAddress());
        return !sourcesBeans.isEmpty();
    }

    @Override
    public int delete(Connection conn, SourcesBean bean) throws SQLException {
        String sql = "DELETE FROM SOURCES WHERE ADDRESS = ?;";
        return super.update(conn,sql,bean.getAddress());
    }

    @Override
    public int truncate(Connection conn) throws SQLException {
        String sql = "TRUNCATE TABLE SOURCES;";
        return super.update(conn,sql);
    }
}
