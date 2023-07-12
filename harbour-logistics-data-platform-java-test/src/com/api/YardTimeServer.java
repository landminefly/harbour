package com.api;

import com.api.viewBean.TotalTrendBean;
import com.api.viewBean.YardTimeBean;
import com.api.viewDAO.impl.TotalTrendDAOImpl;
import com.api.viewDAO.impl.YardTimeDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class YardTimeServer {
    /**
     * 查询YardTime中的所有记录
     * @param conn 数据库连接对象
     * @return 将查询到的记录封装到对应的bean中，再存储到一个List对象中返回
     */
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
