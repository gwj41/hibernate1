package org.robbie.mto;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.robbie.Address;
import org.robbie.BaseTest;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

//@RunWith(BlockJUnit4ClassRunner.class)
public class ManyToOneTest extends BaseTest {
    @Test
    public void saveStudent() throws IOException, CloneNotSupportedException {
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
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("images" + File.separator + "a.jpg");
        Blob image = Hibernate.getLobCreator(getSession()).createBlob(inputStream,inputStream.available());
        student.setPicture(image);
        Grade grade = new Grade();
        grade.setGname("grade1");
        grade.setGdesc("abc");
        student.setGrade(grade);
        Student student1 = (Student) student.clone();
        student1.setPicture(null);
        /*Student student1 = new Student();
        student1.setAge(23);
        student1.setGender("male");
        student1.setName("Robbie Gu");
        student1.setBirthday(new Date());
        student1.setAddress(address);
        student1.setPicture(image);
        student1.setGrade(grade);*/
//        getSession().save(grade);
        getSession().save(student);
        getSession().save(student1);
        inputStream.close();
        getSession().flush();

    }

    @Test
    public void loadStudent() throws SQLException, IOException {
        Student studentEntity = getSession().load(Student.class,new Long(1));
        Grade grade = studentEntity.getGrade();
        System.out.println("Grade name is " + grade.getGname());
        Blob picture = studentEntity.getPicture();
        InputStream inputStream = picture.getBinaryStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream,inputStream.available());
        byte[] buff = new byte[inputStream.available()];
        int in = bufferedInputStream.read(buff,0,inputStream.available());
        File pic = new File("E:" + File.separator + "b.jpg");
        OutputStream outputStream = new FileOutputStream(pic);
        outputStream.write(buff);
        inputStream.close();
        outputStream.close();
    }
}
