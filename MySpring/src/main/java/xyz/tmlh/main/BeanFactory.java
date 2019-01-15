/*
 * $Id: BeanFactor.java, 2019年1月15日 下午8:31:58 TianXin Exp $
 * 
 * Copyright (c) 2018 Vnierlai Technologies Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by Vnierlai or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
 package xyz.tmlh.main;

import xyz.tmlh.config.Bean;

/**
 * <p>
 * Title: BeanFactor
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author TianXin
 * @created 2019年1月15日 下午8:31:58
 * @modified [who date description]
 * @check [who date description]
 */
public interface BeanFactory {
    
    public Object getBean(String name);
    
    public Object createBean(Bean bean);
}
