package xyz.tmlh.main;

import java.util.HashMap;
import java.util.Map;

import xyz.tmlh.config.Bean;


public abstract class AbstractBeanFactory implements BeanFactory{

    /**
     * 获得读取的配置文件中的Map信息
     */
    protected Map<String, Bean> map;
    
    /**
     * 作为IOC容器使用,放置spring放置的对象
     */
    protected Map<String, Object> context = new HashMap<String, Object>();
    
}
