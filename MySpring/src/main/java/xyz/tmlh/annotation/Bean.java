/*
 * $Id: Bean.java, 2019年1月15日 下午4:07:10 TianXin Exp $
 * 
 * Copyright (c) 2018 Vnierlai Technologies Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by Vnierlai or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package xyz.tmlh.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import xyz.tmlh.type.ScopeType;

/**
 * <p>
 * Description:
 * </p>
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    
    String name() default "";
    
    ScopeType scope() default ScopeType.SINGLETON;
    
}
