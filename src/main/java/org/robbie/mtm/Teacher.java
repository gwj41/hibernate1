package org.robbie.mtm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity(name = "mtm_teacher1")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY,include = "all")
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
