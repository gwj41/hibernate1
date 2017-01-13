package org.robbie.mtm2;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "mtm_teacher1")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tid;
    @Column(length = 20)
    private String tname;
    @Column
    private int age;
    @Column(length = 10)
    private String gender;
    @ManyToMany(mappedBy = "teachers")
    private Set<Student> students;
    public Teacher(String tname, String gender, int age) {
        this.tname = tname;
        this.gender = gender;
        this.age = age;
    }
    public Teacher() {
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
