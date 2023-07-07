package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.UnloadDAO;
import com.mvc.bean.CustomerBean;
import com.mvc.bean.UnloadBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnloadDAOImpl extends BaseDAO<CustomerBean> implements UnloadDAO {

    @Override
    public int insert(Connection conn, UnloadBean unloadBean) throws SQLException {
        String sql = "INSERT INTO UNLOAD VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return super.update(conn, sql, unloadBean.getId(), unloadBean.getShip_company(), unloadBean.getShip_name(), unloadBean.getStart_time(), unloadBean.getEnd_time(), unloadBean.getDeparture_time(), unloadBean.getArrival_time(), unloadBean.getPort(), unloadBean.getLading_number(), unloadBean.getContainer_number(), unloadBean.getContainer_size(), unloadBean.getDeparture(), unloadBean.getDestination());
    }

    @Override
    public int[] insertBatch(Connection conn, ArrayList<Object> beans) throws SQLException {
        Object[][] objects = new Object[1000][];
        int i = 0;
        for (Object o : beans) {
            UnloadBean unloadBean = (UnloadBean) o;
            objects[i] = new Object[]{unloadBean.getId(), unloadBean.getShip_company(), unloadBean.getShip_name(), unloadBean.getStart_time(), unloadBean.getEnd_time(), unloadBean.getDeparture_time(), unloadBean.getArrival_time(), unloadBean.getPort(), unloadBean.getLading_number(), unloadBean.getContainer_number(), unloadBean.getContainer_size(), unloadBean.getDeparture(), unloadBean.getDestination()};
            i++;
            if(i == 1000){
                break;
            }
        }
        String sql = "INSERT INTO UNLOAD VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return super.batch(conn, sql, objects);
    }

}
