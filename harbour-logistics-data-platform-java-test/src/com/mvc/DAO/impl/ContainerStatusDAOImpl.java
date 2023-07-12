package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.ContainerStatusDAO;
import com.mvc.bean.ContainerStatusBean;
import com.mvc.bean.CustomerBean;
import com.mvc.bean.LogisticsInfoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContainerStatusDAOImpl extends BaseDAO<ContainerStatusBean> implements ContainerStatusDAO {
    /**
     * 插入一条记录
     *
     * @return 返回受影响的记录数
     */
    @Override
    public int insert(Connection conn, ContainerStatusBean containerStatusBean) throws SQLException {
        String sql = "INSERT INTO UNLOAD VALUES (?,?,?,?,?,?,?,?)";
        return super.update(conn, sql,
                            containerStatusBean.getId(),
                            containerStatusBean.getPort(),
                            containerStatusBean.getContainer_number(),
                            containerStatusBean.getSize(),
                            containerStatusBean.getStatus(),
                            containerStatusBean.getLading_number(),
                            containerStatusBean.getYard(),
                            containerStatusBean.getOperation(),
                            containerStatusBean.getDate()
                           );
    }

    /**
     * 批量插入多条记录
     *
     * @return 返回各条语句受影响的记录数
     */
    @Override
    public int[] insertBatch(Connection conn, ArrayList<Object> beans) throws SQLException {
        //将beans中各个bean的参数分别放入objects二维数组中
        Object[][] objects = new Object[beans.size()][];
        for (int i = 0; i < objects.length; i++) {
            ContainerStatusBean containerStatusBean = (ContainerStatusBean) beans.get(i);
            objects[i] = new Object[]{
                    containerStatusBean.getId(),
                    containerStatusBean.getPort(),
                    containerStatusBean.getContainer_number(),
                    containerStatusBean.getSize(),
                    containerStatusBean.getStatus(),
                    containerStatusBean.getLading_number(),
                    containerStatusBean.getYard(),
                    containerStatusBean.getOperation(),
                    containerStatusBean.getDate()
            };
        }
        String sql = "INSERT INTO CONTAINER_STATUS VALUES (?,?,?,?,?,?,?,?,?)";
        return super.batch(conn, sql, objects);
    }

    /**
     * 清空表
     */
    @Override
    public int truncate(Connection conn) throws SQLException {
        String sql = "TRUNCATE TABLE CONTAINER_STATUS";
        return super.update(conn, sql);
    }
}
