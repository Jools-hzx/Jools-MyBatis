package com.hspedu.hzxmybatis.session;

import com.hspedu.hzxmybatis.config.HzxMybatisConfig;
import com.hspedu.hzxmybatis.executor.Executor;
import com.hspedu.hzxmybatis.executor.MyExecutor;

/**
 * @author Zexi He.
 * @date 2023/4/23 23:05
 * @description:    自定义 SqlSession 类,持有HzxMyBatisConfig 和 Executor 实现类
 */
public class HzxSqlSession {

    private HzxMybatisConfig hzxMybatisConfig;
    private Executor executor;

    public HzxSqlSession() {
        hzxMybatisConfig = HzxMybatisConfig.getInstance();
        executor = new MyExecutor();
    }

    public <T> T queryOne() {
        String sql = "SELECT * FROM monster WHERE id = ?";
        return executor.queryOne(sql, 1);
    }
}
