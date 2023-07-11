package com.api.viewDAO.impl;

import com.api.viewBean.TotalThroughputBean;
import com.api.viewBean.TotalTrendBean;
import com.api.viewDAO.inter.TotalTrendDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TotalTrendDAOImpl extends BaseDAO<TotalTrendBean> implements TotalTrendDAO {
    @Override
    public List<TotalTrendBean> query(Connection conn) throws SQLException {
        String sql = """
                     SELECT CARGO, "YEAR", "MONTH", SUM(TOTAL_WEIGHT) AS TOTAL_WEIGHT
                     FROM TOTAL_TREND
                     GROUP BY CARGO, "YEAR", "MONTH"
                     ORDER BY CARGO, "YEAR", "MONTH";
                     """;
        return super.selectForMulti(conn, sql);
    }
}

