package com.api;

import com.api.viewBean.TotalThroughputBean;
import com.api.viewBean.TotalTrendBean;
import com.api.viewDAO.impl.TotalThroughputDAOImpl;
import com.api.viewDAO.impl.TotalTrendDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TotalTrendServer {
    public List<TotalTrendBean> query(Connection conn){
        List<TotalTrendBean> query = null;
        try {
            query = new TotalTrendDAOImpl().query(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }
}
