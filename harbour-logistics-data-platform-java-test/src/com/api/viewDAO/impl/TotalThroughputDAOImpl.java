package com.api.viewDAO.impl;

import com.api.viewBean.TotalThroughputBean;
import com.api.viewDAO.inter.TotalThroughputDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TotalThroughputDAOImpl extends BaseDAO<TotalThroughputBean> implements TotalThroughputDAO {
    @Override
    public List<TotalThroughputBean> query(Connection conn) throws SQLException {
        String sql = "SELECT * FROM TOTAL_THROUGHPUT;";
        return super.selectForMulti(conn,sql);
    }
}
