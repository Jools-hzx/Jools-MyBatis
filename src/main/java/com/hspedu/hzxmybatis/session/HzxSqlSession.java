package com.hspedu.hzxmybatis.session;

import com.hspedu.hzxmybatis.config.HzxMybatisConfig;
import com.hspedu.hzxmybatis.executor.Executor;
import com.hspedu.hzxmybatis.executor.MyExecutor;
import com.hspedu.hzxmybatis.mapper.MyMapperProxy;

import java.lang.reflect.Proxy;

/**
 * @author Zexi He.
 * @date 2023/4/23 23:05
 * @description: 自定义 SqlSession 类,持有HzxMyBatisConfig 和 Executor 实现类
 */
public class HzxSqlSession {

    private HzxMybatisConfig hzxMybatisConfig;
    private Executor executor;

    public HzxSqlSession() {
        hzxMybatisConfig = HzxMybatisConfig.getInstance();
        executor = new MyExecutor();
    }

    //这里通过代理机制动态获取应该注入的sql语句和参数
    public <T> T queryOne(String sql, Object... parameters) {
        return executor.queryOne(sql, parameters);
    }

    //该方法返回接口类型对应的代理对象实例
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                HzxSqlSession.class.getClassLoader(),
                new Class[]{clazz}, //传入的实参就是接口的Class对象
                new MyMapperProxy()
        );
    }
}
