package com.mvc.DAO.inter;

import com.mvc.bean.CustomerBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ContainerStatusDAO {
    int update(Connection conn, Object... args) throws SQLException;

    CustomerBean selectForOne(Connection conn, Object... args) throws SQLException;

    List<CustomerBean> selectForMulti(Connection conn, Object... args) throws SQLException;

    <E> E getValue(Connection conn, Object... args) throws SQLException;
}
