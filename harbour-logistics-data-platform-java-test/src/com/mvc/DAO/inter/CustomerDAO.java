package com.mvc.DAO.inter;

import com.mvc.bean.CustomerBean;
import com.mvc.bean.UnloadBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDAO {
    int insert(Connection conn, CustomerBean customerBean) throws SQLException;

    int[] insertBatch(Connection conn, List<Object> beans) throws SQLException;
    int[] insertBatchForDistinct(Connection conn, List<Object> beans) throws SQLException;
    int distinct(Connection conn) throws SQLException;
    int truncate(Connection conn) throws SQLException;
}
