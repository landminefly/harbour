package com.api.viewDAO.inter;

import com.api.viewBean.TotalTrendBean;
import com.api.viewBean.YardTimeBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface YardTimeDAO {
    public List<YardTimeBean> query(Connection conn) throws SQLException;
}
