package xyz.tmlh.main;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import xyz.tmlh.config.Bean;
import xyz.tmlh.type.ScopeType;


public abstract class AbstractBeanFactory implements BeanFactory{

    /**
     * 获得读取的配置文件中的Map信息
     */
    protected Map<String, Bean> map;
    
    /**
     * 作为IOC容器使用,放置spring放置的对象
     */
    protected Map<String, Object> context = new HashMap<String, Object>();
    
    
    public <T>T getBean(Class<T> clazz) throws Exception {
        T bean = null;
        int n = 0;
        for (Entry<String, Object> entry : context.entrySet()) {
            if (entry.getValue().getClass() == clazz) {
                bean = (T)entry.getValue();
                n++;
            }
        }

        if (n == 2) {
            throw new RuntimeException("容器中存在多个" + clazz.getName());
        }

        if (n == 0) {
            for (Entry<String, Bean> entry : map.entrySet()) {
                if (entry.getValue().getObj().getClass() == clazz) {
                    if(entry.getValue().getScope().equals(ScopeType.PROTOTYPE)) {
                       T newInstance = (T)entry.getValue().getObj().getClass().newInstance();
                       BeanUtils.copyProperties(newInstance, entry.getValue().getObj());
                       return newInstance; 
                    }
                    return  (T)entry.getValue().getObj();
                }
            }
            throw new RuntimeException("ioc not found " + clazz.getName());
        }

        return bean;
    }
}
