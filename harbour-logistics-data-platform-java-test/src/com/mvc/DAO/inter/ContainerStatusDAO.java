package com.mvc.DAO.inter;

import com.mvc.bean.ContainerStatusBean;
import com.mvc.bean.CustomerBean;
import com.mvc.bean.UnloadBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ContainerStatusDAO {
    int insert(Connection conn, ContainerStatusBean containerStatusBean) throws SQLException;

    int[] insertBatch(Connection conn, ArrayList<Object> beans) throws SQLException;
}
