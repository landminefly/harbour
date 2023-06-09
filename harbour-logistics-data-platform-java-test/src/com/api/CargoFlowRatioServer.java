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
    /**
     * 查询CargoFlowRatio中的所有记录
     * @param conn 数据库连接对象
     * @return 将查询到的记录封装到对应的bean中，再存储到一个List对象中返回
     */
    public List<CargoFlowRatioBean> query(Connection conn, String cargoYypes) {
        List<CargoFlowRatioBean> query = null;
        try {
            query = new CargoFlowRatioDAOImpl().query(conn,cargoYypes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    /**
     * 查询CargoFlowRatio中的所有cargo类型
     * @param conn 数据库连接对象
     * @return 将查询到的所有cargo类型以String的形式存储到一个List对象中返回
     */
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
