package com.mvc.DAO.inter;

import com.mvc.bean.CustomerBean;
import com.mvc.bean.LogisticsCompanyBean;
import com.mvc.bean.UnloadBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LogisticsCompanyDAO {

    int insert(Connection conn, LogisticsCompanyBean logisticsCompanyBean) throws SQLException;

    int[] insertBatch(Connection conn, ArrayList<Object> beans) throws SQLException;
    int truncate(Connection conn) throws SQLException;
}
