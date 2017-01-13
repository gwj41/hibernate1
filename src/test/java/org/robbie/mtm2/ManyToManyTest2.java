package org.robbie.mtm2;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import org.robbie.Address;
import org.robbie.BaseTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

//@RunWith(BlockJUnit4ClassRunner.class)
public class ManyToManyTest2 extends BaseTest {
    @Test
    public void saveStudent() throws IOException, CloneNotSupportedException {
        Student student1 = createStudent();
        Student student2 = createStudent();
        Student student3 = createStudent();
        Student student4 = createStudent();
        Teacher teacher1 = createTeacher();
        Teacher teacher2 = createTeacher();
        Teacher teacher3 = createTeacher();
        Teacher teacher4 = createTeacher();

        Set<Teacher> teachers1 = new HashSet<>();
        teachers1.add(teacher1);
        teachers1.add(teacher2);

        Set<Teacher> teachers2 = new HashSet<>();
        teachers2.add(teacher3);
        teachers2.add(teacher4);

        Set<Teacher> teachers3 = new HashSet<>();
        teachers3.add(teacher1);
        teachers3.add(teacher2);
        teachers3.add(teacher3);
        teachers3.add(teacher4);

        Set<Teacher> teachers4 = new HashSet<>();
        teachers4.add(teacher1);
        teachers4.add(teacher2);
        teachers4.add(teacher4);

        student1.setTeachers(teachers1);
        student2.setTeachers(teachers2);
        student3.setTeachers(teachers3);
        student4.setTeachers(teachers4);

        getSession().save(student1);
        getSession().save(student2);
        getSession().save(student3);
        getSession().save(student4);
        getSession().flush();
    }

    @Test
    public void saveStudent2() throws IOException, CloneNotSupportedException {
        Student student1 = new Student("Robbie Gu","male",21,new Date());
        Student student2 = new Student("Robbie1","male",22,new Date());
        Student student3 = new Student("Robbie1","female",23,new Date());
        Student student4 = new Student("Robbie1","male",24,new Date());
        Teacher teacher1 = new Teacher("Nick","male",31);
        Teacher teacher2 = new Teacher("Tom","female",32);
        Teacher teacher3 = new Teacher("Xin xin","male",35);
        Teacher teacher4 = new Teacher("pang pang","male",38);

        Set<Teacher> teachers1 = new HashSet<>();
        teachers1.add(teacher1);
        teachers1.add(teacher2);

        Set<Teacher> teachers2 = new HashSet<>();
        teachers2.add(teacher3);
        teachers2.add(teacher4);

        Set<Teacher> teachers3 = new HashSet<>();
        teachers3.add(teacher1);
        teachers3.add(teacher2);
        teachers3.add(teacher3);
        teachers3.add(teacher4);

        Set<Teacher> teachers4 = new HashSet<>();
        teachers4.add(teacher1);
        teachers4.add(teacher2);
        teachers4.add(teacher4);

        student1.setTeachers(teachers1);
        student2.setTeachers(teachers2);
        student3.setTeachers(teachers3);
        student4.setTeachers(teachers4);

        getSession().save(student1);
        getSession().save(student2);
        getSession().save(student3);
        getSession().save(student4);
        getSession().flush();
    }

    @Test
    public void loadStudent() throws SQLException, IOException {
        Student student = getSession().load(Student.class,new Long(3));
        if (student == null)
            return;
        Set<Teacher> teachers = student.getTeachers();
        for (Iterator<Teacher> teacherIterator = teachers.iterator(); teacherIterator.hasNext(); ) {
            Teacher teacher = teacherIterator.next();
            System.out.println(teacher.getTname());
        }
    }

    @Test
    public void loadTeacher() throws SQLException, IOException {
        Teacher teacher = getSession().load(Teacher.class,new Long(4));
        if (teacher == null)
            return;
        Set<Student> students = teacher.getStudents();
        for (Iterator<Student> studentIterator = students.iterator(); studentIterator.hasNext(); ) {
            Student student = studentIterator.next();
            System.out.println(student.getName());
        }
    }

    @Test
    public void selectEmpty() {
        Session session = getSession();
        Query query = session.createQuery("select new list(t.tname,t.age) from org.robbie.mtm2.Teacher t where t.students is not empty order by t.age desc,t.gender asc ");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        List<List> lists = query.list();
        for (List list:lists) {
            System.out.println("Name: " + list.get(0) + ",Age: " + list.get(1));
        }
    }

    @Test
    public void selectUniqueResult() {
        Session session = getSession();
        Query query = session.createQuery("select new list(t.tname,t.age) from " +
                "org.robbie.mtm2.Teacher t where t.tname = :name and t.students is not empty ").setParameter("name","Nick");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        List list = (List) query.uniqueResult();
        System.out.println("Name: " + list.get(0) + ",Age: " + list.get(1));
    }

    private Teacher createTeacher() {
        Teacher teacher = new Teacher("Nick","male",21);
        return teacher;
    }

    private Student createStudent() {
        Student student = new Student();
        student.setAge(23);
        student.setGender("male");
        student.setName("Robbie Gu");
        student.setBirthday(new Date());
        Address address = new Address();
        address.setAddress("suzhou");
        address.setPhone("13915594892");
        address.setPostalCode("215151");
        student.setAddress(address);
        return student;
    }
}
