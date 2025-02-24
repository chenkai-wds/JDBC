package com.atguigu.advanced.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidTest {
    @Test
    public void testHardCodeDruid() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/atguigu");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");

        druidDataSource.setInitialSize(10);
        druidDataSource.setMaxActive(20);

        Connection connection = druidDataSource.getConnection();
        System.out.println(connection);

        connection.close();
    }

    @Test
    public void testResourcesDruid() throws Exception {
        Properties properties = new Properties();

        InputStream inputStream = DruidTest.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream);

        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        connection.close();
    }
}
