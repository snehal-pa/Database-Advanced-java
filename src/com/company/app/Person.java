package com.company.app;

import com.company.Annotations.Gender;
import com.company.Annotations.Positive;
import com.company.Annotations.Unique;

public class Person {
    @Unique
    private long personnumber;
    private String name;
    @Positive
    private int age;
    @Gender
    private String gender;

    public Person() {
    }

    public Person( String name, int age, String gender)  {
        //personnumber = System.currentTimeMillis();
        personnumber = 1000000000 +(long)(Math.random()*999999999);

        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
