package com.mvc.DAO.impl;

import com.mvc.DAO.BaseDAO;
import com.mvc.DAO.inter.CustomerDAO;
import com.mvc.bean.CustomerBean;
import com.mvc.bean.LogisticsInfoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl extends BaseDAO<CustomerBean> implements CustomerDAO {
    /**
     * 插入一条记录
     *
     * @return 返回受影响的记录数
     */
    @Override
    public int insert(Connection conn, CustomerBean customerBean) throws SQLException {
        String sql = "INSERT INTO CUSTOMER VALUES (?,?,?,?,?)";
        return super.update(conn, sql,
                            customerBean.getName(),
                            customerBean.getCode(),
                            customerBean.getPhone(),
                            customerBean.getAddress(),
                            customerBean.getId()
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
            CustomerBean customerBean = (CustomerBean) beans.get(i);
            objects[i] = new Object[]{
                    customerBean.getName(),
                    customerBean.getCode(),
                    customerBean.getPhone(),
                    customerBean.getAddress(),
                    customerBean.getId()
            };
        }
        String sql = "INSERT INTO CUSTOMER VALUES (?,?,?,?,?)";
        return super.batch(conn, sql, objects);
    }
}
