package com.hspedu.hzxmybatis.executor;

import com.hspedu.entity.Monster;
import com.hspedu.hzxmybatis.config.HzxMybatisConfig;

import java.sql.*;

/**
 * @author Zexi He.
 * @date 2023/4/23 22:54
 * @description:
 */
public class MyExecutor implements Executor {

    private HzxMybatisConfig hzxMybatisConfig;

    public MyExecutor() {
        hzxMybatisConfig = HzxMybatisConfig.getInstance();
    }

    @Override
    public <T> T queryOne(String sql, Object... parameters) {
        Connection connection = hzxMybatisConfig.buildConnection();
        ResultSet set;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //这里简化，只考虑通过id查询单行数据的sql
            preparedStatement.setString(1, parameters[0].toString());
            set = preparedStatement.executeQuery();
            while (set.next()) {
                //封装成 Monster 对象
                Monster monster = new Monster();
                monster.setEmail(set.getString("email"));
                monster.setBirthday(set.getDate("birthday"));
                monster.setName(set.getString("name"));
                monster.setGender(set.getInt("gender"));
                monster.setAge(set.getInt("age"));
                monster.setSalary(set.getDouble("salary"));
                monster.setId(set.getInt("id"));
                return (T) monster;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
