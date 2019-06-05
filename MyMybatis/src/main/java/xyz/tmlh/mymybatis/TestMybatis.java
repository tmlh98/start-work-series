package xyz.tmlh.mymybatis;

import xyz.tmlh.mymybatis.bean.User;
import xyz.tmlh.mymybatis.config.MySqlsession;
import xyz.tmlh.mymybatis.mapper.UserMapper;

public class TestMybatis {
 
   public static void main(String[] args) {  
       MySqlsession sqlsession=new MySqlsession();  
       UserMapper mapper = sqlsession.getMapper(UserMapper.class);  
       User user = mapper.getUserById("1");  
       System.out.println(user);
   } 
}