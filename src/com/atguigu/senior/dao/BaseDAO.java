package com.atguigu.senior.dao;

import com.atguigu.senior.util.JDBCUtilV2;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 将共性的数据库操作封装到BaseDAO里
 */
public class BaseDAO {
    /**
     * 通用的增删改方法
     * @param sql 调用者要执行的SQL语句
     * @param params SQL语句中占位符要赋值的参数
     * @return 受影响的行数
     */
    public int executeUpdate(String sql, Object... params) throws Exception {
        //通过JDBCUtilV2获取数据库链接
        Connection connection = JDBCUtilV2.getConnection();
        //预编译SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //为占位符赋值，执行SQL，接受返回结果
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        int row = preparedStatement.executeUpdate();
        //释放资源
        preparedStatement.close();
        if (connection.getAutoCommit()) {
            JDBCUtilV2.release();
        }
        //返回结果
        return row;
    }

    /**
     * 通用的查询：
     *      多行多列： List<Employee>
     *      单行多列： Employee
     *      单行单列： 封装的是一个结果
     * 封装过程：
     *      返回类型：泛型，调用者知道，调用时，将此次结果类型告知BaseDAO
     *      返回结果：通用List，可以存储一个（get(0)）或多个结果
     *      结果的封装：反射，要求调用者告知BaseDAO要封装对象的类对象
     */
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... params) throws Exception {
        Connection connection = JDBCUtilV2.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        //执行SQL，接收返回的结果集
        ResultSet resultSet = preparedStatement.executeQuery();
        //获取结果集中的元数据对象
        //包含了列的数量以及每个列的名称
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<T> list = new ArrayList<>();
        //处理结果
        while (resultSet.next()) {
            //循环一次，代表有一行数据，通过反射创建一个对象
            T t = clazz.newInstance();
            for (int i = 1; i <= columnCount; i++) {
                //通过下标获取列的值
                Object value = resultSet.getObject(i);
                //获取到的列的value值，这个值就是t这个对象的某一个属性
                //获取当前拿到的列的名字 = 对象的属性名
                String fieldName = metaData.getColumnLabel(i);
                //通过类对象和fieldName获取要封装的对象的属性
                Field field = clazz.getDeclaredField(fieldName);
                //突破封装的private
                field.setAccessible(true);
                field.set(t, value);
            }
            list.add(t);
        }
        resultSet.close();
        preparedStatement.close();
        if (connection.getAutoCommit()) {
            JDBCUtilV2.release();
        }
        return list;
    }

    public <T> T executeQueryBean(Class<T> clazz, String sql, Object... params) throws Exception {
        List<T> list = this.executeQuery(clazz, sql, params);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
