package xyz.tmlh.mymybatis.mapper;

import xyz.tmlh.mymybatis.bean.User;

/**
 * <p>
 *    
 * </p>
 *
 * @author TianXin
 * @since 2019年6月5日下午5:20:04
 */
public interface UserMapper {

    User getUserById(String id);
    
}
