package xyz.tmlh.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import xyz.tmlh.entity.Bean;
import xyz.tmlh.type.ScopeType;


public abstract class AbstractBeanFactoryHandler implements BeanFactory{

    /**
     * 获得读取的配置文件中的Map信息
     */
    protected Map<String, Bean> map;
    
    /**
     * 作为IOC容器使用,放置spring放置的对象
     */
    protected Map<String, Object> context = new HashMap<String, Object>();
    
    public Object createBean(Bean bean) {
        // 创建该类对象
        Object obj = bean.getObj();
        if (obj == null) {
            throw new RuntimeException(bean.getClassName() + "not found");
        }
        return obj;
    }
    
    @Deprecated
    public <T>T getBean(Class<T> clazz) throws Exception {
        throw new RuntimeException("Please use the method getBean(String beanNme)!");
    }
    
    @Deprecated
    public Object getBean(String name) {
        Object bean = context.get(name);
        // 如果为空说明scope不是singleton,那么容器中是没有的,这里现场创建
        if (bean == null) {
            throw new RuntimeException("Named is " + name + " cannot be found in the ioc!");
        }
        return bean;
    }
    
}
