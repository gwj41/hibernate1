package org.robbie.oto;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.robbie.Address;
import org.robbie.BaseTest;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

/**
 * Unit test for simple App.
 */
//@RunWith(BlockJUnit4ClassRunner.class)
public class OneToOneTest extends BaseTest {
    @Test
    public void saveStudent() throws IOException {
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
        student.setPicture(image);
        Grade grade = new Grade();
        grade.setGname("grade1");
        grade.setGdesc("abc");
        grade.setStudent(student);

        Grade grade2 = new Grade();
        grade2.setGname("grade2");
        grade2.setGdesc("desc2");
        grade2.setStudent(student);

        student.setGrade(grade2);
        getSession().save(grade);
        getSession().save(grade2);
        getSession().save(student);
        getSession().flush();
    }

    @Test
    public void loadStudent() throws SQLException, IOException {
        Student studentEntity = getSession().load(Student.class,new Long(2));
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
