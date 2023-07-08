package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.LogisticsCompanyDAO;
import com.mvc.bean.CustomerBean;
import com.mvc.bean.LogisticsCompanyBean;
import com.mvc.bean.LogisticsInfoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogisticsCompanyDAOImpl extends BaseDAO<LogisticsCompanyBean> implements LogisticsCompanyDAO {
    /**
     * 插入一条记录
     *
     * @return 返回受影响的记录数
     */
    @Override
    public int insert(Connection conn, LogisticsCompanyBean logisticsCompanyBean) throws SQLException {
        String sql = "INSERT INTO LOGISTICS_COMPANY VALUES (?,?,?,?,?,?)";
        return super.update(conn, sql,
                            logisticsCompanyBean.getId(),
                            logisticsCompanyBean.getName(),
                            logisticsCompanyBean.getCode(),
                            logisticsCompanyBean.getPerson(),
                            logisticsCompanyBean.getPhone(),
                            logisticsCompanyBean.getAddress()
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
            LogisticsCompanyBean logisticsCompanyBean = (LogisticsCompanyBean) beans.get(i);
            objects[i] = new Object[]{
                    logisticsCompanyBean.getId(),
                    logisticsCompanyBean.getName(),
                    logisticsCompanyBean.getCode(),
                    logisticsCompanyBean.getPerson(),
                    logisticsCompanyBean.getPhone(),
                    logisticsCompanyBean.getAddress()
            };
        }
        String sql = "INSERT INTO LOGISTICS_COMPANY VALUES (?,?,?,?,?,?)";
        return super.batch(conn, sql, objects);
    }
}
