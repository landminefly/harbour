package com.api;

import com.mvc.DAO.impl.SourcesDAOImpl;
import com.mvc.bean.SourcesBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SourcesServer {

    public int insert(Connection conn, SourcesBean bean){
        int insert = 0;
        try {
            insert = new SourcesDAOImpl().insert(conn, bean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }


    public List<SourcesBean> selectForAll(Connection conn){
        List<SourcesBean> insert = null;
        try {
            insert = new SourcesDAOImpl().selectForAll(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }

    public boolean isExist(Connection conn, SourcesBean bean){
        boolean isExist = true;
        try {
            isExist = new SourcesDAOImpl().isExist(conn, bean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExist;
    }

    public int delete(Connection conn, SourcesBean bean) {
        int insert = 0;
        try {
            insert = new SourcesDAOImpl().delete(conn, bean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }


    public int truncate(Connection conn) {
        int insert = 0;
        try {
            insert = new SourcesDAOImpl().truncate(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }
}
