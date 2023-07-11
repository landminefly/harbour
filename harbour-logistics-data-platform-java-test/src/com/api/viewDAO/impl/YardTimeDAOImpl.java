package com.api.viewDAO.impl;

import com.api.viewBean.YardTimeBean;
import com.api.viewDAO.inter.YardTimeDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class YardTimeDAOImpl extends BaseDAO<YardTimeBean> implements YardTimeDAO {
    @Override
    public List<YardTimeBean> query(Connection conn) throws SQLException {
        String sql = "SELECT * FROM YARD_TIME";
        return super.selectForMulti(conn, sql);
    }
}
