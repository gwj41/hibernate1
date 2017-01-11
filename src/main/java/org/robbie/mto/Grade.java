package org.robbie.mto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "mto_grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gid;
    @Column
    private String gname;
    @Column
    private String gdesc;
/*    @OneToMany(targetEntity = Student.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "gid",referencedColumnName = "GID")
    private Set<Student> students = new HashSet<>();*/

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

/*    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }*/
}
