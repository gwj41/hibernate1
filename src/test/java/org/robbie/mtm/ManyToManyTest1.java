package org.robbie.mtm;

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
public class ManyToManyTest1 extends BaseTest {
    @Test
    public void saveStudent1() throws IOException, CloneNotSupportedException {
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
        Student student1 = new Student("Robbie1","male",21,new Date());
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
    public void selectClauseList() {
        Session session = getSession();
        Query query = session.createQuery("select new list(t.tname,t.age) from org.robbie.mtm.Teacher t");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        List<List> lists = query.list();
        for (List list:lists) {
            System.out.println("Name: " + list.get(0));
            System.out.println("Age: " + list.get(1));
        }
    }

    @Test
    public void selectClauseDistinct() {
        Session session = getSession();
        Query query = session.createQuery("select distinct new map(t.tname as name,t.age as age) from org.robbie.mtm.Teacher t");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        List<Map> results = query.list();
        for (Map result:results) {
            System.out.println("Name: " + result.get("name") + ", Age: " + result.get("age"));
//            System.out.println("Name: " + result.get("0") + ", Age: " + result.get("1"));
        }
    }

    @Test
    public void sessionCacheTest() {
        Query query = getSession().createQuery("from org.robbie.mtm.Student");
        List<Student> students = query.list();
        for (Iterator<Student> studentIterator = students.iterator(); studentIterator.hasNext(); ) {
            Student student = studentIterator.next();
            System.out.println("Id: " + student.getId() + " and Name: " + student.getName());
        }
        // Query from session cache
        Iterator<Student> studentIterator = query.iterate();
        while (studentIterator.hasNext()) {
            Student student = studentIterator.next();
            System.out.println("Iterator results Id: " + student.getId() + " and Name: " + student.getName());
        }
    }

    /**
     * open second level cache and query cache
     */
    @Test
    public void queryCacheTest1() {
        Session session = createSession();
        session.beginTransaction();
        Query query = session.createQuery("select t from org.robbie.mtm.Teacher t");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        List<Teacher> teachers = query.list();
        for (Iterator<Teacher> teacherIterator = teachers.iterator(); teacherIterator.hasNext(); ) {
            Teacher teacher = teacherIterator.next();
            System.out.println("Name: " + teacher.getTname());
        }
        session.getTransaction().commit();
        session.close();
        System.out.println("==============================================================================");
        // Another session
        session = createSession();
        session.beginTransaction();
        query = session.createQuery("select t from org.robbie.mtm.Teacher t");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        teachers = query.list();
        for (Iterator<Teacher> teacherIterator = teachers.iterator(); teacherIterator.hasNext(); ) {
            Teacher teacher = teacherIterator.next();
            System.out.println("Name: " + teacher.getTname());
        }
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Close second level cache, open query cache
     */
    @Test
    public void queryCacheTest2() {
        Session session = createSession();
        session.beginTransaction();
        Query query = session.createQuery("select t.tname,t.age from org.robbie.mtm.Teacher t");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        List<Object[]> names = query.list();
        for (Iterator<Object[]> nameIterator = names.iterator(); nameIterator.hasNext(); ) {
            Object[] fields = nameIterator.next();
            System.out.println("Name: " + fields[0] + ", Age: " + fields[1]);
        }
        session.getTransaction().commit();
        session.close();
        System.out.println("==============================================================================");
        // Another session
        session = createSession();
        session.beginTransaction();
        query = session.createQuery("select t.tname,t.age from org.robbie.mtm.Teacher t");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        names = query.list();
        for (Iterator<Object[]> nameIterator = names.iterator(); nameIterator.hasNext(); ) {
            Object[] fields = nameIterator.next();
            System.out.println("Name: " + fields[0] + ", Age: " + fields[1]);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void sessionFactoryCacheTest() {
        Session session = getSession();
        Student student = session.get(Student.class,new Long(3));
        System.out.println(student.getName());
        session = createSession();
        student = session.get(Student.class,new Long(3));
        System.out.println(student.getName());
        session.close();
    }

    @Test
    public void sessionFactoryCacheTest2() {
        Session session = createSession();
        session.beginTransaction();
        Teacher teacher = session.get(Teacher.class,new Long(3));
        System.out.println(teacher.getTname());
        session.getTransaction().commit();
        session.close();
        session = createSession();
        session.beginTransaction();
        teacher = session.get(Teacher.class,new Long(3));
        System.out.println(teacher.getTname());
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void sessionFactoryCacheTest3() {
        Session session = getSession();
        Teacher teacher = session.load(Teacher.class,new Long(3));
        System.out.println(teacher.getTname());
        session = createSession();
        teacher = session.load(Teacher.class,new Long(3));
        System.out.println(teacher.getTname());
        session.close();
    }

    private Teacher createTeacher() {
        Teacher teacher = new Teacher("Teacher Glen","male",30);
        return teacher;
    }

    private Student createStudent() throws IOException {
/*        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("images" + File.separator + "a.jpg");
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);*/
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
//        student.setPicture(bytes);
//        inputStream.close();
        return student;
    }
}
