package xyz.tmlh.main;

import static org.hamcrest.CoreMatchers.nullValue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import xyz.tmlh.config.Bean;
import xyz.tmlh.config.Property;
import xyz.tmlh.config.parse.AnnotationConfigMange;
import xyz.tmlh.type.ScopeType;

/**
 * <p>
 * Description: 注解版启动
 * </p>
 */
public class AnnotationConfigApplicationContext extends AbstractBeanFactory {

    public AnnotationConfigApplicationContext(Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AnnotationConfigMange configManager = new AnnotationConfigMange();
        // 1.读取配置文件得到需要初始化的Bean信息
        map = configManager.getConfig(clazz);
        // 2.遍历配置,初始化Bean
        init();
    }

    public void init() {
        for (Entry<String, Bean> en : map.entrySet()) {
            String beanName = en.getKey();
            Bean bean = en.getValue();

            Object existBean = context.get(beanName);
            // 当容器中为空并且bean的scope属性为singleton时
            if (existBean == null && bean.getScope().equals(ScopeType.SINGLETON)) {
                // 根据字符串创建Bean对象
                Object beanObj = createBean(bean);
                // 把创建好的bean对象放置到map中去
                context.put(beanName, beanObj);
            }
        }
    }

    public Object createBean(Bean bean) {
        // 创建该类对象
        Object obj = bean.getObj();
        if (obj == null) {
            throw new RuntimeException("没有找到该类" + bean.getClassName());
        }
        return obj;
    }

    @Deprecated
    public Object getBean(String name) {
        Object bean = context.get(name);
        // 如果为空说明scope不是singleton,那么容器中是没有的,这里现场创建
        if (bean == null) {
            throw new RuntimeException("建议使用getBean(Class<T> clazz)");
        }
        return bean;
    }

    

}
