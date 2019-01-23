package xyz.tmlh.servlet.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;

/**
 * Created by TianXin on 2019年1月22日.
 */
public abstract class InitServletProcessor {

    String SCAN_PACKAGE = "scanPackage";

    String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    protected Properties properties = new Properties();

    /**
     * 控制器的全类名
     */
    protected List<String> classNames = new ArrayList<>();

    /**
     * ioc(类名 , 对象)
     */
    protected Map<String, Object> ioc = new HashMap<>();
    
    protected Map<String, Method> handlerMapping = new HashMap<>();

    /**
     * /test/doTest=xyz.tmlh.web.controller.TestController@19836349
     */
    protected Map<String, Object> controllerMap = new HashMap<>();
    
    protected ServletConfig config;


    public Map<String, Object> getIoc() {
        return ioc;
    }


    public List<String> getClassNames() {
        return classNames;
    }

    public Map<String, Method> getHandlerMapping() {
        return handlerMapping;
    }

    public Map<String, Object> getControllerMap() {
        return controllerMap;
    }
    
    
    /**
     * 初始化所有相关联的类,扫描用户设定的包下面所有的类
     */
    public abstract void doLoadConfig(ServletConfig config);
}
