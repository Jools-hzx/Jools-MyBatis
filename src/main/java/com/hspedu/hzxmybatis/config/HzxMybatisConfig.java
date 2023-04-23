package com.hspedu.hzxmybatis.config;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Zexi He.
 * @date 2023/4/23 22:23
 * @description: 该类读取自定义xml文件并得到与数据库的连接
 */
public class HzxMybatisConfig {

    //持有类加载器为其属性
    public static ClassLoader classLoader = HzxMybatisConfig.class.getClassLoader();
    private static volatile HzxMybatisConfig hzxMybatisConfig;

    private HzxMybatisConfig() {

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
}
