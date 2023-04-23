import com.hspedu.entity.Monster;
import com.hspedu.hzxmybatis.config.HzxMybatisConfig;
import com.hspedu.hzxmybatis.executor.MyExecutor;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author Zexi He.
 * @date 2023/4/23 22:27
 * @description:
 */
public class HzxMyBatisTest {

    @Test
    public void buildConnectionTest() {
        HzxMybatisConfig instance = HzxMybatisConfig.getInstance();
        HzxMybatisConfig hzxMybatisConfig = HzxMybatisConfig.getInstance();
        System.out.println(instance);
        System.out.println(hzxMybatisConfig);
        Connection connection = hzxMybatisConfig.buildConnection();
        System.out.println("Connection:" + connection);
    }

    @Test
    public void executorTest() {
        MyExecutor executor = new MyExecutor();
        String sql = "select * from monster where id = ?";
        Object o = executor.queryOne(sql, 1);
        System.out.println("monster:" + (Monster) o);
    }
}
