import com.hspedu.entity.Monster;
import com.hspedu.hzxmybatis.config.HzxMybatisConfig;
import com.hspedu.hzxmybatis.executor.MyExecutor;
import com.hspedu.hzxmybatis.mapper.MapperBean;
import com.hspedu.hzxmybatis.mapper.MonsterMapper;
import com.hspedu.hzxmybatis.session.HzxSqlSession;
import com.hspedu.hzxmybatis.session.HzxSqlSessionFactory;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

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

    @Test
    public void queryBySqlSession() {
        HzxSqlSession session = new HzxSqlSession();
        Object o = session.queryOne("select * from monster where id = ?", 1);
        System.out.println("Monster:" + (Monster) o);
    }

    @Test
    public void scanMapperTest() {
        HzxMybatisConfig hzxMybatisConfig = HzxMybatisConfig.getInstance();
        List<MapperBean> mapperBeanList = hzxMybatisConfig.getMapperBeanList();
        for (MapperBean mapperBean : mapperBeanList) {
            System.out.println("mapperBean:" + mapperBean);
        }
    }

    @Test
    public void useProxyInstanceTest() {
        HzxSqlSession hzxSqlSession = new HzxSqlSession();
        MonsterMapper mapper = hzxSqlSession.getMapper(MonsterMapper.class);
        Monster monster = mapper.queryOne(1);
        System.out.println("monster:" + monster);
    }

    @Test
    public void useFactoryGetSession() {
        HzxSqlSession hzxSqlSession = HzxSqlSessionFactory.open();
        MonsterMapper mapper = hzxSqlSession.getMapper(MonsterMapper.class);
        Monster monster = mapper.queryOne(1);
        System.out.println("monster:" + monster);
    }
}
