package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.LoadDAO;
import com.mvc.bean.CustomerBean;
import com.mvc.bean.LoadBean;
import com.mvc.bean.LogisticsInfoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoadDAOImpl extends BaseDAO<LoadBean> implements LoadDAO {
    /**
     * 插入一条记录
     *
     * @return 返回受影响的记录数
     */
    @Override
    public int insert(Connection conn, LoadBean loadBean) throws SQLException {
        String sql = "INSERT INTO LOAD VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return super.update(conn, sql,
                            loadBean.getId(),
                            loadBean.getShip_company(),
                            loadBean.getShip_name(),
                            loadBean.getStart_time(),
                            loadBean.getEnd_time(),
                            loadBean.getDeparture_time(),
                            loadBean.getArrival_time(),
                            loadBean.getPort(),
                            loadBean.getLading_number(),
                            loadBean.getContainer_number(),
                            loadBean.getContainer_size(),
                            loadBean.getDeparture(),
                            loadBean.getDestination()
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
            LoadBean loadBean = (LoadBean) beans.get(i);
            objects[i] = new Object[]{
                    loadBean.getId(),
                    loadBean.getShip_company(),
                    loadBean.getShip_name(),
                    loadBean.getStart_time(),
                    loadBean.getEnd_time(),
                    loadBean.getDeparture_time(),
                    loadBean.getArrival_time(),
                    loadBean.getPort(),
                    loadBean.getLading_number(),
                    loadBean.getContainer_number(),
                    loadBean.getContainer_size(),
                    loadBean.getDeparture(),
                    loadBean.getDestination()
            };
        }
        String sql = "INSERT INTO LOAD VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return super.batch(conn, sql, objects);
    }
}
