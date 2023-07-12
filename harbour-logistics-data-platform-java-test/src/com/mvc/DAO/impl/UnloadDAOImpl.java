package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.UnloadDAO;
import com.mvc.bean.CustomerBean;
import com.mvc.bean.UnloadBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnloadDAOImpl extends BaseDAO<UnloadBean> implements UnloadDAO {

    /**
     * 插入一条记录
     * @return 返回受影响的记录数
     */
    @Override
    public int insert(Connection conn, UnloadBean unloadBean) throws SQLException {
        String sql = "INSERT INTO UNLOAD VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return super.update(conn, sql,
                            unloadBean.getId(),
                            unloadBean.getShip_company(),
                            unloadBean.getShip_name(),
                            unloadBean.getStart_time(),
                            unloadBean.getEnd_time(),
                            unloadBean.getDeparture_time(),
                            unloadBean.getArrival_time(),
                            unloadBean.getPort(),
                            unloadBean.getLading_number(),
                            unloadBean.getContainer_number(),
                            unloadBean.getContainer_size(),
                            unloadBean.getDeparture(),
                            unloadBean.getDestination()
                           );
    }

    /**
     * 批量插入多条记录
     * @return 返回各条语句受影响的记录数
     */
    @Override
    public int[] insertBatch(Connection conn, ArrayList<Object> beans) throws SQLException {
        //将beans中各个bean的参数分别放入objects二维数组中
        Object[][] objects = new Object[beans.size()][];
        for (int i = 0; i<objects.length; i++) {
            UnloadBean unloadBean = (UnloadBean) beans.get(i);
            objects[i] = new Object[]{
                    unloadBean.getId(),
                    unloadBean.getShip_company(),
                    unloadBean.getShip_name(),
                    unloadBean.getStart_time(),
                    unloadBean.getEnd_time(),
                    unloadBean.getDeparture_time(),
                    unloadBean.getArrival_time(),
                    unloadBean.getPort(),
                    unloadBean.getLading_number(),
                    unloadBean.getContainer_number(),
                    unloadBean.getContainer_size(),
                    unloadBean.getDeparture(),
                    unloadBean.getDestination()
            };
        }
        String sql = "INSERT INTO UNLOAD VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return super.batch(conn, sql, objects);
    }

    /**
     * 清空表
     */
    @Override
    public int truncate(Connection conn) throws SQLException {
        String sql = "TRUNCATE TABLE UNLOAD";
        return super.update(conn, sql);
    }
}
