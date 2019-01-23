package xyz.tmlh.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.tmlh.annotation.Controller;
import xyz.tmlh.annotation.RequestMapping;
import xyz.tmlh.annotation.RequestParam;

/**  
 * Created by TianXin on 2019年1月22日. 
 */

@Controller
@RequestMapping("/test")
public class TestController {
    
    @RequestMapping("/doTest")
    public void test1(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("param") String param){
     System.out.println(param);
      try {
            response.getWriter().write( "doTest method success! param:"+param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   @RequestMapping("/doTest2")
    public void test2(HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().println("doTest2 method success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
