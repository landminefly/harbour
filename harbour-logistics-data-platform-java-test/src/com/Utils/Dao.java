package com.Utils;

import java.sql.Connection;
import java.util.List;

public class Dao extends BaseDao<CUSTOMER_Bean>{

    public int update(Connection conn, String sql, Object ... args)
    {
        return super.update(conn,sql,args);
    }

    public CUSTOMER_Bean selectForOneReturnsBean(Connection conn, String sql, Object ... args)
    {
        return super.selectForOneReturnsBean(conn,sql,args);
    }

    public List<CUSTOMER_Bean> selectNotForOneReturnsBeanList(Connection conn, String sql, Object ... args)
    {
        return super.selectNotForOneReturnsBeanList(conn,sql,args);
    }

    public <E> E getValue(Connection conn, String sql, Object ... args)
    {
        return super.getValue(conn,sql,args);
    }

}
