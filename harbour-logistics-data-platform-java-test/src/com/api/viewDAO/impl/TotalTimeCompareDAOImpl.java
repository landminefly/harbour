package com.api.viewDAO.impl;

import com.api.viewBean.TotalTimeCompareBean;
import com.api.viewDAO.inter.TotalTimeCompareDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TotalTimeCompareDAOImpl extends BaseDAO<TotalTimeCompareBean> implements TotalTimeCompareDAO {
    @Override
    public List<TotalTimeCompareBean> query(Connection conn) throws SQLException {
        String sql = "SELECT * FROM TOTAL_TIME_COMPARE";
        return super.selectForMulti(conn, sql);
    }
}
