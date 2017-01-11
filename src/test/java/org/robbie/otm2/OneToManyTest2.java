package org.robbie.otm2;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.robbie.Address;
import org.robbie.BaseTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//@RunWith(BlockJUnit4ClassRunner.class)
public class OneToManyTest2 extends BaseTest {
    @Test
    public void saveStudent() throws IOException, CloneNotSupportedException {
        Student student = new Student();
//        student.setId(new Long(3));
        student.setAge(23);
        student.setGender("male");
        student.setName("Robbie Gu");
        student.setBirthday(new Date());
        Address address = new Address();
        address.setAddress("suzhou");
        address.setPhone("13915594892");
        address.setPostalCode("215151");
        student.setAddress(address);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("images" + File.separator + "a.jpg");
        Blob image = Hibernate.getLobCreator(getSession()).createBlob(inputStream,inputStream.available());
        Blob image1 = Hibernate.getLobCreator(getSession()).createBlob(inputStream,inputStream.available());
        student.setPicture(image);
        Grade grade = new Grade();
        grade.setGname("grade1");
        grade.setGdesc("abc");
        Student student1 = (Student) student.clone();
        student1.setPicture(image1);
        Set<Student> students = new HashSet<>();
        students.add(student);
        students.add(student1);
        grade.setStudents(students);
        getSession().save(grade);
        inputStream.close();
    }

    @Test
    public void loadGrade() throws SQLException, IOException {
        Grade grade = getSession().load(Grade.class,new Long(1));
        Set<Student> students = grade.getStudents();
        for (Iterator<Student> studentIterator = students.iterator(); studentIterator.hasNext(); ) {
            Student next = studentIterator.next();
            System.out.println(next.getName());
        }
    }

    @Test
    public void loadStudent() throws SQLException, IOException {
        Student student = getSession().load(Student.class,new Long(2));
        System.out.println(student.getGrade().getGname());
    }
}
