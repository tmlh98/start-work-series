package xyz.tmlh.test;

import xyz.tmlh.annotation.Bean;
import xyz.tmlh.annotation.Configuration;
import xyz.tmlh.entity.Person;
import xyz.tmlh.entity.Student;
import xyz.tmlh.entity.Teacher;
import xyz.tmlh.type.ScopeType;

@Configuration
public class AnnotationConfig {

    @Bean
    public Student student() {
        Student student = new Student();
        student.setName("zhangsan");
        return student;
    }
    
    @Bean(name = "student2")
    public Student student1() {
        Student student = new Student();
        student.setName("zhangsan");
        return student;
    }

    @Bean
    public Teacher teacher() {
        Teacher teacher = new Teacher();
        teacher.setStudent(student());
        return teacher;
    }

    @Bean(scope = ScopeType.PROTOTYPE)
    public Person person() {
        Person person = new Person();
        person.setStudent(student());
        person.setTeacher(teacher());
        return person;
    }

}
