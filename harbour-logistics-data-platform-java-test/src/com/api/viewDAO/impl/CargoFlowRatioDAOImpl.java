package com.api.viewDAO.impl;

import com.api.viewBean.CargoFlowRatioBean;
import com.api.viewDAO.inter.CargoFlowRatioDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CargoFlowRatioDAOImpl extends BaseDAO<CargoFlowRatioBean> implements CargoFlowRatioDAO {
    @Override
    public List<CargoFlowRatioBean> query(Connection conn, String cargoType) throws SQLException {
        String sql = """
                     SELECT PORT, WEIGHT FROM CARGO_FLOW_RATIO WHERE CARGO = ? ORDER BY PORT;
                     """;
        return super.selectForMulti(conn, sql, cargoType);
    }

    @Override
    public List<CargoFlowRatioBean> queryForCargoTypes(Connection conn) throws SQLException {
        String sql = """
                     SELECT CARGO FROM CARGO_FLOW_RATIO GROUP BY CARGO;
                     """;
        return super.selectForMulti(conn, sql);
    }
}
