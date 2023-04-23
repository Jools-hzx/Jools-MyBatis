package com.hspedu.hzxmybatis.config;

import com.hspedu.hzxmybatis.mapper.Function;
import com.hspedu.hzxmybatis.mapper.MapperBean;
import lombok.Getter;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zexi He.
 * @date 2023/4/23 22:23
 * @description: 该类读取自定义xml文件并得到与数据库的连接
 */
@Getter
public class HzxMybatisConfig {

    //持有类加载器为其属性
    public static ClassLoader classLoader = HzxMybatisConfig.class.getClassLoader();
    private static volatile HzxMybatisConfig hzxMybatisConfig;
    //该集合用于存放封装成的 MapperBean 对象
    private List<MapperBean> mapperBeanList = new ArrayList<>();

    private HzxMybatisConfig() {
        //扫描 XxxMapper.xml 文件并解析
        readMapper();
    }

    //返回单例对象
    public static synchronized HzxMybatisConfig getInstance() {
        if (null != hzxMybatisConfig) {
            return hzxMybatisConfig;
        } else {
            hzxMybatisConfig = new HzxMybatisConfig();
            return hzxMybatisConfig;
        }
    }


    public Connection buildConnection() {

        InputStream resourceAsStream = classLoader.getResourceAsStream("hzx-mybatis-config.xml");
        SAXReader reader = new SAXReader();
        //使用som4j解析xml资源文件流
        try {
            Document document = reader.read(resourceAsStream);
            //读取root
            Element rootElement = document.getRootElement();
//            System.out.println("rootElement:" + rootElement);
            //得到 database 标签内的所有 property 标签
            List property = rootElement.elements("property");
            String driver = "";
            String url = "";
            String username = "";
            String password = "";
            for (Object e : property) {
                Element element = (Element) e;
                //得到 property 标签内 name 属性的值
                String name = element.attributeValue("name");
//                System.out.println("name:" + name);
                String value = element.attributeValue("value");
                switch (name) {
                    case "driverClassName":
                        driver = value;
                        break;
                    case "url":
                        url = value;
                        break;
                    case "username":
                        username = value;
                        break;
                    case "password":
                        password = value;
                        break;
                    default:
                        System.out.println("Can not resolve attribute name:" + name);
                }
            }
            try {
                Class<?> clazz = Class.forName(driver);
                System.out.println("驱动名:" + clazz.getName());
                System.out.println("url:" + url);
                System.out.println("用户名:" + username);
                System.out.println("密码:" + password);
                Connection connection = DriverManager.getConnection(url, username, password);
                if (null != connection) {
                    return connection;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void readMapper() {
        SAXReader saxReader = new SAXReader();
        InputStream resourceAsStream = classLoader.getResourceAsStream("MonsterMapper.xml");
        try {
            //使用Dom4j解析 MonsterMapper.xml 文件
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            //rootElement = mapper
            //其下
            List elements = rootElement.elements();
            MapperBean mapperBean = new MapperBean();
            List<Function> functionList = new ArrayList<>();
            //取出该Mapper.xml文件所配置的接口名
            String namespace = rootElement.attributeValue("namespace").trim();
            mapperBean.setInterfaceName(namespace);

            for (Object e : elements) {
                //可能配置了多个同接口下的方法
                Element element = (Element) e;
                String sqlType = "";
                if ("select".equals(element.getName())) {
                     sqlType = "select";
                }
                //取出XML文件下的一些配置
                String functionName = element.attributeValue("id").trim();
                String parameterType = element.attributeValue("parameterType").trim();
                String resultType = element.attributeValue("resultType").trim();
                String sql = element.getText().trim();

                //封装成Function
                Function function = new Function();
                function.setFunctionName(functionName);
                function.setSql(sql);
                function.setResultType(Class.forName(resultType).newInstance());
                function.setParameterType(parameterType);
                function.setSqlType(sqlType);
                functionList.add(function);
            }
            mapperBean.setFunctionList(functionList);
            //将封装好的 MapperBean 添加到集合中
            mapperBeanList.add(mapperBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
