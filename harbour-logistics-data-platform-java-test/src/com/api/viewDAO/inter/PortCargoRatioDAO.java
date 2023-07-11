package com.api.viewDAO.inter;

import com.api.viewBean.PortCargoRatioBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PortCargoRatioDAO {

    public List<PortCargoRatioBean> query(Connection conn) throws SQLException;
}
