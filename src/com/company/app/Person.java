package com.company.app;

import com.company.db.Unique;

import java.io.Serializable;

public class Person implements Serializable {
    @Unique
    private long personnumber;
    private String name;
    @Positive
    private int age;
    private String gender;

    public Person() {
    }

    public Person( String name, int age, String gender)  {
        //this.personnumber = pn;
        //personnumber = System.currentTimeMillis();
        personnumber = 1000000000 +(long)(Math.random()*999999999);
        //Thread.sleep(200);
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
