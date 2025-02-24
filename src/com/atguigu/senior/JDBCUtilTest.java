package com.atguigu.senior;

import com.atguigu.senior.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;

public class JDBCUtilTest {
    @Test
    public void testGetConnection() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
        JDBCUtil.release(connection);
    }
}
