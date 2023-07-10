package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.LogisticsInfoDAO;
import com.mvc.bean.LogisticsInfoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogisticsInfoDAOImpl extends BaseDAO<LogisticsInfoBean> implements LogisticsInfoDAO {
    /**
     * 插入一条记录
     *
     * @return 返回受影响的记录数
     */
    @Override
    public int insert(Connection conn, LogisticsInfoBean logisticsInfoBean) throws SQLException {
        String sql = "INSERT INTO LOGISTICS_INFORMATION VALUES (?,?,?,?,?,?,?,?)";
        return super.update(conn, sql,
                            logisticsInfoBean.getId(),
                            logisticsInfoBean.getLading_number(),
                            logisticsInfoBean.getCustomer_name(),
                            logisticsInfoBean.getCustomer_code(),
                            logisticsInfoBean.getCompany(),
                            logisticsInfoBean.getContainer_number(),
                            logisticsInfoBean.getCargo(),
                            logisticsInfoBean.getWeight()
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
            LogisticsInfoBean logisticsInfoBean = (LogisticsInfoBean) beans.get(i);
            objects[i] = new Object[]{
                    logisticsInfoBean.getId(),
                    logisticsInfoBean.getLading_number(),
                    logisticsInfoBean.getCustomer_name(),
                    logisticsInfoBean.getCustomer_code(),
                    logisticsInfoBean.getCompany(),
                    logisticsInfoBean.getContainer_number(),
                    logisticsInfoBean.getCargo(),
                    logisticsInfoBean.getWeight()
            };
        }
        String sql = "INSERT INTO LOGISTICS_INFORMATION VALUES (?,?,?,?,?,?,?,?)";
        return super.batch(conn, sql, objects);
    }
}
