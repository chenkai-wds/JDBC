package com.atguigu.advanced.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class HikariTest {
    @Test
    public void testHardCodeHikari() throws Exception{
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/atguigu");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("123456");

        hikariDataSource.setMinimumIdle(10);
        hikariDataSource.setMaximumPoolSize(20);

        Connection connection = hikariDataSource.getConnection();
        System.out.println(connection);

        connection.close();
    }

    @Test
    public void testResourcesHikari() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = HikariTest.class.getClassLoader().getResourceAsStream("hikari.properties");
        properties.load(inputStream);

        HikariConfig hikariConfig = new HikariConfig(properties);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        Connection connection = hikariDataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
}
