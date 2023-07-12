package com.api;

import com.api.viewBean.TotalTimeCompareBean;
import com.api.viewBean.TotalTrendBean;
import com.api.viewDAO.impl.TotalTimeCompareDAOImpl;
import com.api.viewDAO.impl.TotalTrendDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TotalTimeCompareServer {
    /**
     * 查询TotalTimeCompare中的所有记录
     * @param conn 数据库连接对象
     * @return 将查询到的记录封装到对应的bean中，再存储到一个List对象中返回
     */
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
