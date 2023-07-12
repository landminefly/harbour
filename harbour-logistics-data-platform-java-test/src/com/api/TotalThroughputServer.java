package com.api;

import com.api.viewBean.TotalThroughputBean;
import com.api.viewDAO.impl.TotalThroughputDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TotalThroughputServer {
    /**
     * 查询TotalThroughput中的所有记录
     * @param conn 数据库连接对象
     * @return 将查询到的记录封装到对应的bean中，再存储到一个List对象中返回
     */
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
