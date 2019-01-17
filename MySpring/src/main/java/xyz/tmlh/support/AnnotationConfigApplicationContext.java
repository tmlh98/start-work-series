package xyz.tmlh.support;

import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import xyz.tmlh.config.AnnotationConfigMange;
import xyz.tmlh.entity.Bean;
import xyz.tmlh.type.ScopeType;

/**
 * <p>
 * Description: 注解版启动
 * </p>
 */
public class AnnotationConfigApplicationContext extends AbstractBeanFactoryHandler {

    public AnnotationConfigApplicationContext(Class<?> clazz) throws Exception {
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

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) throws Exception {
        T bean = null;
        int n = 0;
        for (Entry<String, Object> entry : context.entrySet()) {
            if (entry.getValue().getClass() == clazz) {
                bean = (T)entry.getValue();
                n++;
            }
        }
        if (n == 2) {
            throw new RuntimeException("容器中存在多个" + clazz.getSimpleName());
        }
        if (n == 0) {
            for (Entry<String, Bean> entry : map.entrySet()) {
                if (entry.getValue().getObj().getClass() == clazz) {
                    if (entry.getValue().getScope().equals(ScopeType.PROTOTYPE)) {
                        T newInstance = (T)entry.getValue().getObj().getClass().newInstance();
                        BeanUtils.copyProperties(newInstance, entry.getValue().getObj());
                        return newInstance;
                    }
                    return (T)entry.getValue().getObj();
                }
            }
            throw new RuntimeException("ioc not found " + clazz.getName());
        }

        return bean;
    }

}
