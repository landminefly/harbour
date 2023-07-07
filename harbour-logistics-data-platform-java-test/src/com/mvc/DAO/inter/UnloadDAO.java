package com.mvc.DAO.inter;

import com.mvc.bean.CustomerBean;
import com.mvc.bean.UnloadBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UnloadDAO {
    int insert(Connection conn, UnloadBean unloadBean) throws SQLException;

    public int[] insertBatch(Connection conn, ArrayList<Object> beans) throws SQLException;
}
