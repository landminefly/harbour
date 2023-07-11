package com.api;

import com.api.viewBean.CargoFlowRatioBean;
import com.api.viewBean.PortCargoRatioBean;
import com.api.viewDAO.impl.CargoFlowRatioDAOImpl;
import com.api.viewDAO.impl.PortCargoRatioDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoFlowRatioServer {
    public List<CargoFlowRatioBean> query(Connection conn, String cargoYypes) {
        List<CargoFlowRatioBean> query = null;
        try {
            query = new CargoFlowRatioDAOImpl().query(conn,cargoYypes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    public List<String> queryForCargoTypes(Connection conn){
        List<CargoFlowRatioBean> query = null;
        try {
            query = new CargoFlowRatioDAOImpl().queryForCargoTypes(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for(CargoFlowRatioBean item : query){
            arrayList.add(item.getCargo());
        }
        return arrayList;
    }
}
