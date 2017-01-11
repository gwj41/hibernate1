package org.robbie.mtm;

import org.robbie.Address;
import org.robbie.mto.Grade;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.Set;

@Entity(name = "mtm_student1")
public class Student implements Serializable,Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String name;
    @Column
    private String gender;
    @Column
    @Basic(fetch = FetchType.EAGER)
    private int age;
    @Column
    @Temporal(value = TemporalType.DATE)
    private Date birthday;
    @Column
    @Lob
    private Blob picture;
    @Embedded
    private Address address;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "teachers_students",schema = "hibernate",
            joinColumns = {@JoinColumn(name = "Student_id")},inverseJoinColumns = {@JoinColumn(name = "Teacher_id")})
    private Set<Teacher> teachers;

//    public StudentEntity() {
//    }
//
//    public StudentEntity(long id, String name, String gender, int age) {
//        this.id = id;
//        this.name = name;
//        this.gender = gender;
//        this.age = age;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
