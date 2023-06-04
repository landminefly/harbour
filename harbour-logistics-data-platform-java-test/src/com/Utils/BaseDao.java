package com.Utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao {

    //非DQL 通用的增、删、改的方法
    //String sql：sql
    //Object... args：给sql中的?设置的值列表，可以是0~n
    protected int executeUpdate(String sql,Object... params) throws SQLException {
        //创建PreparedStatement对象，对sql预编译
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        //设置?的值
        if(params != null && params.length > 0){
            for(int i = 0; i < params.length; i++) {
                ps.setObject(i+1, params[i]);//?的编号从1开始，不是从0开始，数组的下标是从0开始
            }
        }

        //执行sql
        int len = ps.executeUpdate();
        ps.close();
        //这里检查下是否开启事务,开启不关闭连接,业务方法关闭!
        //没有开启事务的话,直接回收关闭即可!
        if (connection.getAutoCommit()) {
            //回收
            JdbcUtils.freeConnection();
        }
        return len;
    }

    //DQL
    //<T>声明一个泛型，不确定类型
    //1.确定泛型User.class T=User
    //2.要使用反射技术给属性赋值
    //public <T>List<T>
    //executeQuery(class<T> clazz,String sql,Object...params);
    protected <T> List<T> executeQuery(Class<T> clazz, String sql, Object... args) throws Exception {
        //创建PreparedStatement对象，对sql预编译
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        //设置?的值
        if(args != null && args.length > 0){
            for(int i=0; i<args.length; i++) {
                ps.setObject(i+1, args[i]);//?的编号从1开始，不是从0开始，数组的下标是从0开始
            }
        }

        List<T> list = new ArrayList<>();
        ResultSet res = ps.executeQuery();
        /*
        获取结果集的元数据对象。
        元数据对象中有该结果集一共有几列、列名称是什么等信息
         */
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();//获取结果集列数

        //遍历结果集ResultSet，把查询结果中的一条一条记录，变成一个一个T对象，放到list中
        while(res.next()){
            //循环一次代表有一行，代表有一个T对象
            T t = clazz.newInstance();//要求这个类型必须有公共的无参构造

            //把这条记录的每一个单元格的值取出来，设置到t对象对应的属性中。
            for(int i=1; i<=columnCount; i++){
                //for循环一次，代表取某一行的1个单元格的值
                Object value = res.getObject(i);

                //这个值应该是t对象的某个属性值
                //获取该属性对应的Field对象
                //String columnName = metaData.getColumnName(i);//获取第i列的字段名
                String columnName = metaData.getColumnLabel(i);//获取第i列的字段名或字段的别名
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);//这么做可以操作private的属性
                field.set(t, value);
            }
            list.add(t);
        }

        res.close();
        ps.close();
        //这里检查下是否开启事务,开启不关闭连接,业务方法关闭!
        //没有开启事务的话,直接回收关闭即可!
        if (connection.getAutoCommit()) {
            //回收
            JdbcUtils.freeConnection();
        }
        return list;
    }
}