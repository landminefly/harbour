package com.api.viewDAO.inter;

import com.api.viewBean.CustomerViewBean;
import com.api.viewBean.LogisticsInfoViewBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerViewDAO {
    public List<CustomerViewBean> query(Connection conn, String[] args, long PageNo) throws SQLException;
    public long queryCount(Connection conn, String[] args, long PageNo) throws SQLException;
}
