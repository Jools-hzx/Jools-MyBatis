package com.hspedu.hzxmybatis.executor;

import java.sql.Statement;

/**
 * @author Zexi He.
 * @date 2023/4/23 22:53
 * @description:    执行器接口
 */
public interface Executor {

    <T> T queryOne(String sql, Object... parameters);
}
