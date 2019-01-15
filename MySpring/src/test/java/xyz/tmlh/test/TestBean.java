package xyz.tmlh.test;

import org.junit.Test;

import xyz.tmlh.entity.Person;
import xyz.tmlh.entity.Student;
import xyz.tmlh.main.BeanFactory;
import xyz.tmlh.main.ClassPathXmlApplicationContext;


public class TestBean {

    @Test
    public void func1() {
        BeanFactory bf = new ClassPathXmlApplicationContext("/applicationContext.xml");
        
        Person s = (Person)bf.getBean("person");
        Person s1 = (Person)bf.getBean("person");
        
        System.out.println(s == s1);
        System.out.println(s1);
        
        Student stu1 = (Student)bf.getBean("student");
        Student stu2 = (Student)bf.getBean("student");
        String name = stu1.getName();
        System.out.println(name);
        System.out.println(stu1 == stu2);
    }
}