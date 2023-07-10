package com.api.viewDAO.inter;

import com.api.viewBean.LogisticsInfoViewBean;
import com.api.viewBean.TotalThroughputBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TotalThroughputDAO {
    public List<TotalThroughputBean> query(Connection conn) throws SQLException;
}
