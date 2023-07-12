package com.mvc.DAO.inter;

import com.mvc.bean.UnloadBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UnloadDAO {
    int insert(Connection conn, UnloadBean unloadBean) throws SQLException;

    int[] insertBatch(Connection conn, ArrayList<Object> beans) throws SQLException;

    int truncate(Connection conn) throws SQLException;
}
