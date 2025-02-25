package com.atguigu.senior.dao.impl;

import com.atguigu.senior.dao.BankDao;
import com.atguigu.senior.dao.BaseDAO;

public class BankDaoImpl extends BaseDAO implements BankDao {
    @Override
    public int subMoney(Integer id, Integer money) {
        try {
            String sql = "update t_bank set money = money + ? where id = ?";
            return executeUpdate(sql, money, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addMoney(Integer id, Integer money) {
        try {
            String sql = "update t_bank set money = money - ? where id = ?";
            return executeUpdate(sql, money, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
