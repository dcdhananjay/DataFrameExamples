package com.mycompany.jdbcexample;

import java.util.Date;

public class Person {

    private int id;
    private String fname;
    private String lname;
    private int age;
    private Date dob;

    public Person() {
    }

    public Person(int id, String fname, String lname, int age) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Id = " + id + ", First Name = " + fname + ", Last Name = " + lname + ", Age = " + age + ", Date of Birth = " + dob;
    }

}
