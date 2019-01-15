package xyz.tmlh.type;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Description: bean的作用域
 * </p>
 */
public enum ScopeType {

    /**
     * 原型
     */
    PROTOTYPE,
    
    /**
     * 单例
     */
    SINGLETON;
    
    
    public static ScopeType getScopt(String name){
        if(StringUtils.equalsIgnoreCase(name, PROTOTYPE.toString())) {
            return PROTOTYPE;
        }
        return SINGLETON;
    }
}
