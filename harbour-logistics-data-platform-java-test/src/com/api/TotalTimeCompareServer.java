package com.api;

import com.api.viewBean.TotalTimeCompareBean;
import com.api.viewBean.TotalTrendBean;
import com.api.viewDAO.impl.TotalTimeCompareDAOImpl;
import com.api.viewDAO.impl.TotalTrendDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TotalTimeCompareServer {
    public List<TotalTimeCompareBean> query(Connection conn){
        List<TotalTimeCompareBean> query = null;
        try {
            query = new TotalTimeCompareDAOImpl().query(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }
}
