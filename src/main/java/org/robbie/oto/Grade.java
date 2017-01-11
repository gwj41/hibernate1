package org.robbie.oto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "oto_grade")
@Table
public class Grade implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gid;
    @Column
    private String gname;
    @Column
    private String gdesc;
    @OneToOne(mappedBy = "grade",cascade = CascadeType.ALL)
    private Student student;

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGdesc() {
        return gdesc;
    }

    public void setGdesc(String gdesc) {
        this.gdesc = gdesc;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
