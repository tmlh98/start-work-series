package xyz.tmlh.servlet.support;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import xyz.tmlh.annotation.Controller;
import xyz.tmlh.annotation.RequestMapping;

/**
 * 处理器映射器 Created by TianXin on 2019年1月22日.
 */
public class HandlerMappingHolder implements HandlerMapping {

    private InitServletProcessor initServletProcessor;

    public HandlerMappingHolder() {
        super();
    }

    public HandlerMappingHolder(InitServletProcessor initServletProcessor) {
        this.initServletProcessor = initServletProcessor;
    }
    
    public static HandlerMappingHolder getInstance(InitServletProcessor initServletProcessor) {
        return new HandlerMappingHolder(initServletProcessor);
    }

    @Override
    public void initHandlerMapping() {
        Map<String, Object> ioc = initServletProcessor.getIoc();

        Map<String, Method> handlerMapping = initServletProcessor.getHandlerMapping();

        /**
         * /test/doTest=xyz.tmlh.web.controller.TestController@19836349
         */
        Map<String, Object> controllerMap = initServletProcessor.getControllerMap();

        if (ioc.isEmpty()) {
            return;
        }
        try {
            for (Entry<String, Object> entry : ioc.entrySet()) {
                Class<? extends Object> clazz = entry.getValue().getClass();
                if (!clazz.isAnnotationPresent(Controller.class)) {
                    continue;
                }

                // 拼url时,是controller头的url拼上方法上的url
                String baseUrl = "";
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
                    baseUrl = annotation.value();
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(RequestMapping.class)) {
                        continue;
                    }
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    String url = annotation.value();

                    url = (baseUrl + "/" + url).replaceAll("/+", "/");
                    handlerMapping.put(url, method);
                    controllerMap.put(url, clazz.newInstance());
                    System.out.println(url + "," + method);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
