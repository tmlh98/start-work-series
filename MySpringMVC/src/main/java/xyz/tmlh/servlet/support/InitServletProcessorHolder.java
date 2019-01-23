package xyz.tmlh.servlet.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletConfig;

import xyz.tmlh.annotation.Controller;
import xyz.tmlh.util.StringUtil;

/**
 * Created by TianXin on 2019年1月22日.
 */
public class InitServletProcessorHolder extends InitServletProcessor {
    

    @Override
    public void doLoadConfig(ServletConfig config) {
        String packageName = config.getInitParameter(CONTEXT_CONFIG_LOCATION);
        System.out.println("packageName : " + packageName);
        
        //把web.xml中的contextConfigLocation对应value值的文件加载到流里面
        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(packageName);){
            // 用Properties文件加载文件里的内容
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取控制器的全类名
        doScanner(properties.get(SCAN_PACKAGE).toString());
        //初始化ioc
        doInstance();
        
        HandlerMappingHolder.getInstance(this).initHandlerMapping();

        
    }

    /**
     * 获取控制器的全类名
     */
    private void doScanner(String packageName) {
        // 把所有的.替换成/
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // 递归读取包
                doScanner(packageName + "." + file.getName());
            } else {
                String className = packageName + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }
    
    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames) {
            try {
                // 把类搞出来,反射来实例化(只有加@Controller需要实例化)
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    ioc.put(StringUtil.toLowerFirstWord(clazz.getSimpleName()), clazz.newInstance());
                } else {
                    continue;
                }

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
