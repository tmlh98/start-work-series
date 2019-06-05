package xyz.tmlh.mymybatis.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import xyz.tmlh.mymybatis.bean.MapperBean;

public class MyMapperProxy implements InvocationHandler {

    private MySqlsession mySqlsession;

    private Configuration configuration;

    public MyMapperProxy(Configuration myConfiguration, MySqlsession mySqlsession) {
        this.configuration = myConfiguration;
        this.mySqlsession = mySqlsession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean readMapper = configuration.readMapper("UserMapper.xml");
        
        // 是否是xml文件对应的接口
        if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())) {
            return null;
        }
        
        List<Function> list = readMapper.getList();
        if (!list.isEmpty()) {
            for (Function function : list) {
                // id是否和接口方法名一样
                if (method.getName().equals(function.getFunctionName())) {
                    return mySqlsession.selectOne(function.getSql(), String.valueOf(args[0]));
                }
            }
        }
        return null;
    }
}