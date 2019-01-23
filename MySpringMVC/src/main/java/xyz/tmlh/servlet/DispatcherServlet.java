package xyz.tmlh.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.tmlh.servlet.support.InitServletProcessor;
import xyz.tmlh.servlet.support.InitServletProcessorHolder;

/**
 * 中央控制器
 * Created by TianXin on 2019年1月22日.
 */
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 7970358970459002459L;

    /**
     * 控制层所有的映射信息
     */
    private Map<String, Method> handlerMapping;

    private Map<String, Object> controllerMap;

    @Override
    public void init(ServletConfig config) throws ServletException {
        InitServletProcessor initProcessor = new InitServletProcessorHolder();
        initProcessor.doLoadConfig(config);
        handlerMapping = initProcessor.getHandlerMapping();
        controllerMap = initProcessor.getControllerMap();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 处理请求
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500!! Server Exception");
        }

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (handlerMapping.isEmpty()) {
            return;
        }

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();

        url = url.replace(contextPath, "").replaceAll("/+", "/");

        if (!this.handlerMapping.containsKey(url)) {
            resp.getWriter().write("404 NOT FOUND!");
            return;
        }

        Method method = this.handlerMapping.get(url);

        // 获取方法的参数列表
        Class<?>[] parameterTypes = method.getParameterTypes();

        // 获取请求的参数
        Map<String, String[]> parameterMap = req.getParameterMap();

        // 保存参数值
        Object[] paramValues = new Object[parameterTypes.length];

        // 方法的参数列表
        for (int i = 0; i < parameterTypes.length; i++) {
            // 根据参数名称，做某些处理
            String requestParam = parameterTypes[i].getSimpleName();

            if (requestParam.equals("HttpServletRequest")) {
                // 参数类型已明确，这边强转类型
                paramValues[i] = req;
                continue;
            }
            if (requestParam.equals("HttpServletResponse")) {
                paramValues[i] = resp;
                continue;
            }
            if (requestParam.equals("String")) {
                for (Entry<String, String[]> param : parameterMap.entrySet()) {
                    String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                    paramValues[i] = value;
                }
            }
        }
        // 利用反射机制来调用
        try {
            method.invoke(this.controllerMap.get(url), paramValues);// 第一个参数是method所对应的实例 在ioc容器中
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
