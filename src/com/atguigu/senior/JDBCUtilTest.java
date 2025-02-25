package com.atguigu.senior;

import com.atguigu.senior.dao.EmployeeDao;
import com.atguigu.senior.dao.impl.EmployeeDaoImpl;
import com.atguigu.senior.pojo.Employee;
import com.atguigu.senior.util.JDBCUtil;
import com.atguigu.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class JDBCUtilTest {
    @Test
    public void testGetConnection() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
        JDBCUtil.release(connection);
    }

    @Test
    public void testJDBCV2() {
        Connection connection1 = JDBCUtilV2.getConnection();
        Connection connection2 = JDBCUtilV2.getConnection();
        Connection connection3 = JDBCUtilV2.getConnection();

        System.out.println(connection1);
        System.out.println(connection2);
        System.out.println(connection3);

        JDBCUtilV2.release();
    }

    @Test
    public void testEmployeeDao() {
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        /*
        //调用查询所有方法
        List<Employee> employeeList = employeeDao.selectAll();
        for (Employee employee : employeeList) {
            System.out.println("employee = " + employee);
        }*/

        /*
        //调用查询单个员工方法
        Employee employee = employeeDao.selectByEmpId(1);
        System.out.println("employee = " + employee);
        */

        /*
        //调用添加员工的方法
        Employee employee = new Employee(null, "tom", 300.65, 38);
        int insert = employeeDao.insert(employee);
        System.out.println("insert = " + insert);
        */

        /*//调用修改员工数据方法
        Employee employee = new Employee(8, "tom", 656.65, 38);
        int update = employeeDao.update(employee);
        System.out.println("update = " + update);
        */

        //调用删除员工方法
        int delete = employeeDao.delete(8);
        System.out.println("delete = " + delete);
    }
}
