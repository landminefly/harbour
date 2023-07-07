package com.mvc.DAO;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description 使用了Apache提供的DbUtils
 */
public class BaseDAO<T> {
    //BaseDAO的泛型
    private Class<T> clazz = null;
    //只需一个QueryRunner对象即可，因此将其声明为一个属性
    private QueryRunner queryRunner = new QueryRunner();

    //获取BaseDAO的泛型类型
    {
        Type genericSuperclass1 = this.getClass().getGenericSuperclass();
        ParameterizedType genericSuperclass2 = (ParameterizedType) genericSuperclass1;
        Type[] actualTypeArguments = genericSuperclass2.getActualTypeArguments();
        clazz = (Class<T>) actualTypeArguments[0];
    }


    public int[] batch(Connection conn, String sql, Object[][] args) throws SQLException {
        return queryRunner.batch(conn, sql, args);
    }


    /**
     * @return 返回影响的记录条数
     * @Description 实现通用的增删改操作
     */
    public int update(Connection conn, String sql, Object... args) throws SQLException {
        return queryRunner.update(conn, sql, args);
    }

    /**
     * @Description 实现通用的查询操作（仅返回一条记录）
     */
    public T selectForOne(Connection conn, String sql, Object... args) throws SQLException {
        return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
    }

    /**
     * @Description 实现通用的查询操作（返回多条记录）
     */
    public List<T> selectForMulti(Connection conn, String sql, Object... args) throws SQLException {
        return queryRunner.query(conn, sql, new BeanListHandler<>(clazz), args);
    }

    /**
     * @Description 实现特殊值的查询操作
     */
    public <E> E getValue(Connection conn, String sql, Object... args) throws SQLException {
        return queryRunner.query(conn, sql, new ScalarHandler<E>(), args);
    }
}
