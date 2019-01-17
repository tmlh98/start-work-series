package xyz.tmlh.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import xyz.tmlh.config.Bean;
import xyz.tmlh.config.Property;
import xyz.tmlh.config.parse.XmlConfigManager;
import xyz.tmlh.type.ScopeType;

public class ClassPathXmlApplicationContext extends AbstractBeanFactory {

    /**
     * 获得读取的配置文件中的Map信息
    */
    protected Map<String, Bean> map;

    public ClassPathXmlApplicationContext(String path) {
        XmlConfigManager xmlConfigManager = new XmlConfigManager();
        // 1.读取配置文件得到需要初始化的Bean信息
        map = xmlConfigManager.getConfig(path);
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
        Class<?> clazz = null;
        try {
            clazz = Class.forName(bean.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("没有找到该类" + bean.getClassName());
        }
        Object beanObj = null;
        try {
            beanObj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("没有提供无参构造器");
        }
        // 获得bean的属性,将其注入
        if (bean.getProperties() != null) {
            for (Property prop : bean.getProperties()) {
                // 注入分两种情况
                // 获得要注入的属性名称
                String name = prop.getName();
                String value = prop.getValue();
                String ref = prop.getRef();
                // 使用BeanUtils工具类完成属性注入,可以自动完成类型转换
                // 如果value不为null,说明有
                if (value != null) {
                    Map<String, String[]> parmMap = new HashMap<String, String[]>();
                    parmMap.put(name, new String[] {value});
                    try {
                        BeanUtils.populate(beanObj, parmMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("请检查你的" + name + "属性");
                    }
                }

                if (ref != null) {
                    // 根据属性名获得一个注入属性对应的set方法
                    // Method setMethod = BeanUtil.getWriteMethod(beanObj,
                    // name);

                    // 看一看当前IOC容器中是否已存在该bean,有的话直接设置没有的话使用递归,创建该bean对象
                    Object existBean = context.get(prop.getRef());
                    if (existBean == null) {
                        // 递归的创建一个bean
                        existBean = createBean(map.get(prop.getRef()));
                        // 放置到context容器中
                        // 只有当scope="singleton"时才往容器中放
                        if (map.get(prop.getRef()).getScope().equals("singleton")) {
                            context.put(prop.getRef(), existBean);
                        }
                    }
                    try {
                        // setMethod.invoke(beanObj, existBean);
                        // 通过BeanUtils为beanObj设置属性
                        BeanUtils.setProperty(beanObj, name, existBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("您的bean的属性" + name + "没有对应的set方法");
                    }

                }

            }
        }

        return beanObj;
    }

    public Object getBean(String name) {
        Object bean = context.get(name);
        // 如果为空说明scope不是singleton,那么容器中是没有的,这里现场创建
        if (bean == null) {
            bean = createBean(map.get(name));
        }
        return bean;
    }


}