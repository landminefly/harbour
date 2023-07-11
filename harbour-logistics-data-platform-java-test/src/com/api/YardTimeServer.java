package com.api;

import com.api.viewBean.TotalTrendBean;
import com.api.viewBean.YardTimeBean;
import com.api.viewDAO.impl.TotalTrendDAOImpl;
import com.api.viewDAO.impl.YardTimeDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class YardTimeServer {
    public List<YardTimeBean> query(Connection conn){
        List<YardTimeBean> query = null;
        try {
            query = new YardTimeDAOImpl().query(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }
}
