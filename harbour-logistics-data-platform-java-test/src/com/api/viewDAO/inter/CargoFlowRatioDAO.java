package com.api.viewDAO.inter;

import com.api.viewBean.CargoFlowRatioBean;
import com.api.viewBean.TotalTrendBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CargoFlowRatioDAO {
    public List<CargoFlowRatioBean> query(Connection conn, String cargoType) throws SQLException;

    public List<CargoFlowRatioBean> queryForCargoTypes(Connection conn) throws SQLException;
}
