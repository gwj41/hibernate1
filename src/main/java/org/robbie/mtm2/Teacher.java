package org.robbie.mtm2;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "mtm_teacher1")
public class Teacher {
    public Teacher(String tname) {
        this.tname = tname;
    }

    public Teacher() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tid;
    @Column(length = 20)
    private String tname;
    @ManyToMany(mappedBy = "teachers")
    private Set<Student> students;

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
}
