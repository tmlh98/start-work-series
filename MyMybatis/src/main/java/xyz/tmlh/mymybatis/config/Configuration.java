package xyz.tmlh.mymybatis.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import xyz.tmlh.mymybatis.bean.MapperBean;

/**
 * <p>
 * 读取解析配置信息
 * </p>
 *
 * @author TianXin
 * @since 2019年6月5日下午5:21:48
 */
public class Configuration {

    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml信息并处理
     */
    public Connection build(String resource) {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occured while evaling xml " + resource);
        }
    }

    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!node.getName().equals("database")) {
            throw new RuntimeException("root should be <database>");
        }
        
        Map<String, String> dbMap = new HashMap<>(4);
        
        // 获取属性节点
        for (Object item : node.elements("property")) {
            Element i = (Element)item;
            String name = i.attributeValue("name");
            String value = getValue(i);
            if (name == null || value == null) {
                throw new RuntimeException("[database]: <property> should contain name and value");
            }
            dbMap.put(name, value);
        }
        
        String driverClassName = dbMap.get("driverClassName");
        String url = dbMap.get("url");
        String username = dbMap.get("username");
        String password = dbMap.get("password");

        Class.forName(driverClassName);
        
        Connection connection = null;
        try {
            // 建立数据库链接
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    @SuppressWarnings("rawtypes")
    public MapperBean readMapper(String path) {
        MapperBean mapper = new MapperBean();
        try {
            InputStream stream = loader.getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            mapper.setInterfaceName(root.attributeValue("namespace").trim()); // 把mapper节点的nameSpace值存为接口名
            List<Function> list = new ArrayList<Function>(); // 用来存储方法的List
            for (Iterator rootIter = root.elementIterator(); rootIter.hasNext();) {// 遍历根节点下所有子节点
                Function fun = new Function(); // 用来存储一条方法的信息
                Element e = (Element)rootIter.next();
                String sqltype = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();
                fun.setSqlType(sqltype);
                fun.setFunctionName(funcName);
                Object newInstance = null;
                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                fun.setResultType(newInstance);
                fun.setSql(sql);
                list.add(fun);
            }
            mapper.setList(list);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return mapper;
    }

}
