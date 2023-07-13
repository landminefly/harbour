package com.api;

import com.mvc.DAO.impl.SourcesDAOImpl;
import com.mvc.bean.SourcesBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SourcesServer {

    /**
     * 向Sources表中插入一条记录
     * @param conn DM8连接
     * @param bean SourcesBean对象
     * @return
     */
    public int insert(Connection conn, SourcesBean bean){
        int insert = 0;
        try {
            insert = new SourcesDAOImpl().insert(conn, bean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }

    /**
     * 从Sources表中查询全部记录
     * @param conn DM8连接
     * @return
     */
    public List<SourcesBean> selectForAll(Connection conn){
        List<SourcesBean> insert = null;
        try {
            insert = new SourcesDAOImpl().selectForAll(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }

    /**
     * 向Sources表查询是否存在一条记录，查询依据是address字段
     * @param conn DM8连接
     * @param bean SourcesBean对象，除了address属性以外，可以为null
     * @return
     */
    public boolean isExist(Connection conn, SourcesBean bean){
        boolean isExist = true;
        try {
            isExist = new SourcesDAOImpl().isExist(conn, bean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExist;
    }

    /**
     * 从Sources表中删除一条记录，删除依据是address字段
     * @param conn DM8连接
     * @param bean SourcesBean对象，除了address属性以外，可以为null
     * @return
     */
    public int delete(Connection conn, SourcesBean bean) {
        int insert = 0;
        try {
            insert = new SourcesDAOImpl().delete(conn, bean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }

    /**
     * 清空Sources表
     * @param conn DM8连接
     * @return
     */
    public int truncate(Connection conn) {
        int insert = 0;
        try {
            insert = new SourcesDAOImpl().truncate(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }
}
