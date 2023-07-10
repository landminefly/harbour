package com.api.viewDAO.impl;

import com.api.viewBean.LogisticsCompanyViewBean;
import com.api.viewDAO.inter.LogisticsCompanyViewDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LogisticsCompanyViewDAOImpl extends BaseDAO<LogisticsCompanyViewBean> implements LogisticsCompanyViewDAO {
    @Override
    public List<LogisticsCompanyViewBean> query(Connection conn, String[] args, long pageNo) throws SQLException {
        String sql = """
                     SELECT NAME, CODE, PERSON, PHONE, ADDRESS
                     FROM LOGISTICS_COMPANY
                     WHERE NAME LIKE ?
                       AND CODE LIKE ?
                       AND PERSON LIKE ?
                       AND PHONE LIKE ?
                       AND ADDRESS LIKE ?
                     LIMIT ?,20;
                     """;
        return super.selectForMulti(conn, sql,
                                    "%" + args[0] + "%",
                                    "%" + args[1] + "%",
                                    "%" + args[2] + "%",
                                    "%" + args[3] + "%",
                                    "%" + args[4] + "%",
                                    (pageNo-1)*20
                                   );
    }

    @Override
    public long queryCount(Connection conn, String[] args, long pageNo) throws SQLException {
        String sql = """
                     SELECT COUNT(*)
                     FROM LOGISTICS_COMPANY
                     WHERE NAME LIKE ?
                       AND CODE LIKE ?
                       AND PERSON LIKE ?
                       AND PHONE LIKE ?
                       AND ADDRESS LIKE ?;
                     """;
        return super.getValue(conn, sql,
                                    "%" + args[0] + "%",
                                    "%" + args[1] + "%",
                                    "%" + args[2] + "%",
                                    "%" + args[3] + "%",
                                    "%" + args[4] + "%"
                                   );
    }
}
