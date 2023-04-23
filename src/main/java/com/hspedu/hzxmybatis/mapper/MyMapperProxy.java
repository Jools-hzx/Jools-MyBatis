package com.hspedu.hzxmybatis.mapper;

import com.hspedu.hzxmybatis.config.HzxMybatisConfig;
import com.hspedu.hzxmybatis.session.HzxSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Zexi He.
 * @date 2023/4/24 0:01
 * @description:
 */
public class MyMapperProxy implements InvocationHandler {

    private HzxMybatisConfig hzxMybatisConfig;
    private HzxSqlSession hzxSqlSession;

    public MyMapperProxy() {
        hzxMybatisConfig = HzxMybatisConfig.getInstance();
        hzxSqlSession = new HzxSqlSession();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //取出待执行的方法名
        String name = method.getName();

        //遍历Config 中存储的 MapperBean
        List<MapperBean> mapperBeanList = hzxMybatisConfig.getMapperBeanList();
        for (MapperBean mapperBean : mapperBeanList) {
            //如果存在该接口对应的配置 MapperBean,执行操作
            if (method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())) {
                //遍历该MapperBean中存放的所有 Function 元素
                List<Function> functionList = mapperBean.getFunctionList();
                for (Function function : functionList) {
                    //简化处理，仅根据方法名判断。
                    if (function.getFunctionName().equals(name)) {
                        //这里简化处理，我们的 Executor 仅有一个实现类和实现方法, 为select 语句并且参数仅有一个
                        //简化模拟Mybatis 机制，使用Executor 完成对数据库的操作
                        String sql = function.getSql();
                        return hzxSqlSession.queryOne(sql, args[0]);
                    }
                }
            }
        }
        return null;
    }
}
