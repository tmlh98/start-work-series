package xyz.tmlh.test;

import org.junit.Test;

import xyz.tmlh.entity.Person;
import xyz.tmlh.entity.Student;
import xyz.tmlh.support.AnnotationConfigApplicationContext;
import xyz.tmlh.support.BeanFactory;
import xyz.tmlh.support.ClassPathXmlApplicationContext;

public class TestBean {

    @Test
    public void xml() throws Exception {
        
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
        
//        Person student = bf.getBean(Person.class);
//        System.out.println(student);
        
    }
    
    @Test
    public void annotation() throws Exception {
        
        BeanFactory bf = new AnnotationConfigApplicationContext(AnnotationConfig.class);
        Object bean = null;
        Object bean2 = null;
        try {
            bean = bf.getBean(Person.class);
            bean2 = bf.getBean(Person.class);
            
//            Student student2 = (Student)bf.getBean("student1");
//            System.out.println(student2);
//            Student student = bf.getBean(Student.class);
//            System.out.println(student);
        } catch (Exception e) {
             e.printStackTrace();
        }
        
        System.out.println(bean == bean2);
        System.out.println(bean);
    }
}