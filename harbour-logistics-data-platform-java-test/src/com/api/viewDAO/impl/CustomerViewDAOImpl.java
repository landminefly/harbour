package com.api.viewDAO.impl;

import com.api.viewBean.CustomerViewBean;
import com.api.viewDAO.inter.CustomerViewDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CustomerViewDAOImpl extends BaseDAO<CustomerViewBean> implements CustomerViewDAO{
    @Override
    public List<CustomerViewBean> query(Connection conn, String[] args, long pageNo) throws SQLException {
        String sql = """
                     SELECT NAME, CODE, PHONE, ADDRESS
                                   FROM CUSTOMER
                                   WHERE NAME LIKE ?
                                     AND CODE LIKE ?
                                     AND PHONE LIKE ?
                                     AND ADDRESS LIKE ?
                                   LIMIT ?, 20;
                     """;
        System.out.println(Arrays.toString(args));
        return super.selectForMulti(conn, sql,
                                    "%" + args[0] + "%",
                                    "%" + args[1] + "%",
                                    "%" + args[2] + "%",
                                    "%" + args[3] + "%",
                                    (pageNo-1)*20
                                   );
    }

    @Override
    public long queryCount(Connection conn, String[] args, long pageNo) throws SQLException {
        String sql = """
                     SELECT COUNT(*)
                     FROM CUSTOMER
                     WHERE NAME LIKE ?
                       AND CODE LIKE ?
                       AND PHONE LIKE ?
                       AND ADDRESS LIKE ?;
                     """;
        return super.getValue(conn, sql,
                              "%" + args[0] + "%",
                              "%" + args[1] + "%",
                              "%" + args[2] + "%",
                              "%" + args[3] + "%"
                             );
    }
}
