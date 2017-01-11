package org.robbie.mtm;

import javax.persistence.*;

@Entity(name = "mtm_teacher1")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tid;
    @Column(length = 20)
    private String tname;

    public Teacher(String tname) {
        this.tname = tname;
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
}
