package com.mvc.DAO.inter;

import com.mvc.DAO.BaseDAO;
import com.mvc.bean.SourcesBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SourcesDAO {
    int insert(Connection conn, SourcesBean bean) throws SQLException;
    List<SourcesBean> selectForAll(Connection conn) throws SQLException;

    boolean isExist(Connection conn, SourcesBean bean) throws SQLException;
    int delete(Connection conn,SourcesBean bean) throws SQLException;
    int truncate(Connection conn) throws SQLException;
}
