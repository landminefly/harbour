package com.api.viewDAO.impl;

import com.api.viewBean.PortCargoRatioBean;
import com.api.viewBean.TotalTimeCompareBean;
import com.api.viewDAO.inter.PortCargoRatioDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PortCargoRatioDAOImpl extends BaseDAO<PortCargoRatioBean> implements PortCargoRatioDAO {
    @Override
    public List<PortCargoRatioBean> query(Connection conn) throws SQLException {
        String sql = "SELECT CARGO, SUM(TOTAL_WEIGHT) AS TOTAL_WEIGHT FROM PORT_CARGO_RATIO GROUP BY CARGO;";
        return super.selectForMulti(conn, sql);
    }
}
