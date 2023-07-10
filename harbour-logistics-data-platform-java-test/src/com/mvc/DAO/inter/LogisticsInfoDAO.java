package com.mvc.DAO.inter;

import com.api.viewBean.LogisticsInfoViewBean;
import com.mvc.bean.LogisticsInfoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LogisticsInfoDAO {

    int insert(Connection conn, LogisticsInfoBean logisticsInfoBean) throws SQLException;

    int[] insertBatch(Connection conn, ArrayList<Object> beans) throws SQLException;

}
