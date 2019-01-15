package xyz.tmlh.config;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description:用于封装Bean标签信息的Bean类
 * </p>
 */
public class Bean {

    private String name;
    private String className;
    private String scope = "singleton";
    
    private List<Property> properties = new ArrayList<Property>();

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}