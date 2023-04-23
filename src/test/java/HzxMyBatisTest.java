import com.hspedu.hzxmybatis.config.HzxMybatisConfig;
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
}
