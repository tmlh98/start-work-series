package xyz.tmlh.test;

import java.lang.reflect.InvocationTargetException;
import java.text.Annotation;

import org.junit.Test;

import xyz.tmlh.entity.Person;
import xyz.tmlh.entity.Student;
import xyz.tmlh.main.AnnotationConfigApplicationContext;
import xyz.tmlh.main.BeanFactory;
import xyz.tmlh.main.ClassPathXmlApplicationContext;


public class TestBean {

    @Test
    public void xml() {
        
        BeanFactory bf = new ClassPathXmlApplicationContext("/applicationContext.xml");
        
        Person s = (Person)bf.getBean("person");
        Person s1 = (Person)bf.getBean("person");
        
        System.out.println(s == s1);
        System.out.println(s1);
        
        Student stu1 = (Student)bf.getBean("student1");
        Student stu2 = (Student)bf.getBean("student");
        String name = stu1.getName();
        
        System.out.println(name);
        System.out.println(stu1 == stu2);
        
    }
    @Test
    public void annotation() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        BeanFactory bf = new AnnotationConfigApplicationContext(AnnotationConfig.class);
        Object bean = bf.getBean("student2");
        
        System.out.println(bean);
    }
}