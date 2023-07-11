package com.api.viewDAO.inter;

import com.api.viewBean.TotalTimeCompareBean;
import com.api.viewBean.TotalTrendBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TotalTimeCompareDAO {
    public List<TotalTimeCompareBean> query(Connection conn) throws SQLException;
}
