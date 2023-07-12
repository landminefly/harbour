package com.api;

import com.api.viewBean.PortCargoRatioBean;
import com.api.viewBean.TotalTrendBean;
import com.api.viewDAO.impl.PortCargoRatioDAOImpl;
import com.api.viewDAO.impl.TotalTrendDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PortCargoRatioServer {
    /**
     * 查询PortCargoRatio中的所有记录
     * @param conn 数据库连接对象
     * @return 将查询到的记录封装到对应的bean中，再存储到一个List对象中返回
     */
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
