package com.api.viewDAO.impl;

import com.api.viewBean.LogisticsCompanyViewBean;
import com.api.viewDAO.inter.LogisticsCompanyViewDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LogisticsCompanyViewDAOImpl implements LogisticsCompanyViewDAO {
    @Override
    public List<LogisticsCompanyViewBean> query(Connection conn, String[] args, long PageNo) throws SQLException {
        return null;
    }

    @Override
    public long queryCount(Connection conn, String[] args, long PageNo) throws SQLException {
        return 0;
    }
}
