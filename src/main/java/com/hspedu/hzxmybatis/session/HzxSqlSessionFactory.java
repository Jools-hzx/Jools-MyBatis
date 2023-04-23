package com.hspedu.hzxmybatis.session;

/**
 * @author Zexi He.
 * @date 2023/4/24 0:22
 * @description:    SqlSession的工厂类
 */
public class HzxSqlSessionFactory {

    public static HzxSqlSession open() {
        return new HzxSqlSession();
    }
}
