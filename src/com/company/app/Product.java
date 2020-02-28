package com.company.app;


import com.company.Annotations.Positive;
import com.company.Annotations.Unique;

public class Product {
    @Unique
    private long modelNumber;
    private String name;
    @Positive
    private float price;

    public Product(){

    }

    public Product(long modelNumber, String name, float price) {
        this.modelNumber = modelNumber;
        //this.modelNumber = 1000000000 +(long)(Math.random()*999999999);
        this.name = name;
        this.price = price;
        this.modelNumber = modelNumber;
    }

}
