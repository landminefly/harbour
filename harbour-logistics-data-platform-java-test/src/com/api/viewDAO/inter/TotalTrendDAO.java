package com.api.viewDAO.inter;

import com.api.viewBean.TotalThroughputBean;
import com.api.viewBean.TotalTrendBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TotalTrendDAO {
    public List<TotalTrendBean> query(Connection conn) throws SQLException;
}
