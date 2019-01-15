package xyz.tmlh.config;

import java.util.ArrayList;
import java.util.List;

import xyz.tmlh.type.ScopeType;

/**
 * <p>
 * Description:用于封装Bean标签信息的Bean类
 * </p>
 */
public class Bean {

    /**
     * 唯一标识id
     */
    private String id;
    
    /**
     * 全类名
     */
    private String className;
    
    /**
     * 已经创建的对象
     */
    private Object obj;
    
    
    /**
     * 作用域 prototype,singleton
     */
    private ScopeType scope = ScopeType.SINGLETON;
    
    /**
     * bean的properties属性信息
     */
    private List<Property> properties = new ArrayList<Property>();

    public ScopeType getScope() {
        return scope;
    }

    public void setScope(ScopeType scope) {
        this.scope = scope;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Bean [id=" + id + ", className=" + className + ", obj=" + obj + ", scope=" + scope + ", properties=" + properties + "]";
    }


}