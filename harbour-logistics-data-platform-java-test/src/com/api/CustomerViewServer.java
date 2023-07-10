package com.api;

import com.api.viewBean.CustomerViewBean;
import com.api.viewBean.LogisticsInfoViewBean;
import com.api.viewDAO.impl.CustomerViewDAOImpl;
import com.api.viewDAO.impl.LogisticsInfoViewDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerViewServer {
    /**
     * 根据筛选条件查询Customer表中符合条件的记录条数
     * @param conn 数据库连接对象
     * @param args 与前端筛选器中的筛选条件一一对应，如果不需要某一个筛选条件，则应传入""，而不是null
     * @param pageNo 需要查询的页码
     * @return 将查询到的记录封装到对应的bean中，再存储到一个List对象中返回
     */
    public List<CustomerViewBean> query(Connection conn, String[] args, long pageNo){

        List<CustomerViewBean> query = null;
        try {
            query = new CustomerViewDAOImpl().query(conn, args, pageNo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    /**
     * 根据筛选条件查询Customer表中符合条件的记录条数
     * @param conn 数据库连接对象
     * @param args 与前端筛选器中的筛选条件一一对应，如果不需要某一个筛选条件，则应传入""，而不是null
     * @param pageNo 需要查询的页码
     * @return 返回符合条件的记录条数
     */
    public long queryCount(Connection conn,String[] args,long pageNo){
        long count = 0;
        try {
            count = new CustomerViewDAOImpl().queryCount(conn, args, pageNo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
