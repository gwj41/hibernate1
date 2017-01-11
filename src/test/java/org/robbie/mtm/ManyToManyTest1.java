package org.robbie.mtm;

import org.junit.Test;
import org.robbie.Address;
import org.robbie.BaseTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//@RunWith(BlockJUnit4ClassRunner.class)
public class ManyToManyTest1 extends BaseTest {
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

    private Teacher createTeacher() {
        Teacher teacher = new Teacher("Teacher Glen");
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
