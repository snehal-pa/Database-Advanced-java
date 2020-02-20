package com.company.app;

import com.company.db.Unique;

import java.io.Serializable;

public class Person implements Serializable {
    @Unique
    private String personnumber;
    private String Name;
    private String age;
    private String gender;

    public Person() {
    }

    public Person(String name, String age, String gender)  {
        personnumber = Long.toString(1000000000 +(long)(Math.random()*999999999));
        //Thread.sleep(200);
        Name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getPersonnumber() {
        return personnumber;
    }

    public String getName() {
        return Name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

}
