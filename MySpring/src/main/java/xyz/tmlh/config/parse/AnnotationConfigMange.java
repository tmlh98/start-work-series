package xyz.tmlh.config.parse;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import xyz.tmlh.annotation.Bean;
import xyz.tmlh.annotation.Configuration;
import xyz.tmlh.config.Property;
import xyz.tmlh.type.ScopeType;

public class AnnotationConfigMange{


    /**
     * 读取配置文件并返回读取结果
     * 返回Map集合便于注入,key是每个Bean的name属性,value是对应的那个Bean对象
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    public Map<String, xyz.tmlh.config.Bean> getConfig(Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, xyz.tmlh.config.Bean> map = new HashMap<String, xyz.tmlh.config.Bean>();
        
        if(!isExistAnnotation(clazz.getAnnotations())) {
            throw new RuntimeException("not found annotation Configuration");
        }
        //获取类中的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Bean beanMethod = method.getAnnotation(Bean.class);
            if(beanMethod != null) {
                xyz.tmlh.config.Bean bean = new xyz.tmlh.config.Bean();
                bean.setId(method.getName());
                try {
                    Object newInstance = clazz.newInstance();
                    bean.setObj( method.invoke(newInstance, null));
                    bean.setScope(beanMethod.scope());
                    if(StringUtils.isEmpty(beanMethod.name())) {
                        map.put(method.getName(), bean);
                    }else {
                        map.put(beanMethod.name(), bean);
                    }
                    
                } catch (InstantiationException e) {
                     e.printStackTrace();
                }
            }
        }
        return map;
    }

    private boolean isExistAnnotation(Annotation[] annotations){
        for (Annotation annotation : annotations) {
            if(Configuration.class == annotation.annotationType()) {
                return true;
            }
        }
        return false;
    }
    
}
