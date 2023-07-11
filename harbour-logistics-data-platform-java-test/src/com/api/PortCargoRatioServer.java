package com.api;

import com.api.viewBean.PortCargoRatioBean;
import com.api.viewBean.TotalTrendBean;
import com.api.viewDAO.impl.PortCargoRatioDAOImpl;
import com.api.viewDAO.impl.TotalTrendDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PortCargoRatioServer {
    public List<PortCargoRatioBean> query(Connection conn){
        List<PortCargoRatioBean> query = null;
        try {
            query = new PortCargoRatioDAOImpl().query(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }
}
