package com.api;

import com.api.viewBean.TotalThroughputBean;
import com.api.viewDAO.impl.TotalThroughputDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TotalThroughputServer {
    public List<TotalThroughputBean> query(Connection conn){
        List<TotalThroughputBean> query = null;
        try {
            query = new TotalThroughputDAOImpl().query(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }
}
