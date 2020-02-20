package com.company.app;

import com.company.db.Unique;

import java.io.Serializable;

public class Product implements Serializable {
    @Unique
    private long modelNumber;
    private String name;
    private float price;


    public Product(String name, float price) {
        this.modelNumber = 1000000000 +(long)(Math.random()*999999999);
        this.name = name;
        this.price = price;
        this.modelNumber = modelNumber;
    }

    public String getName() {
        return name;
    }


    public float getPrice() {
        return price;
    }

    public long getModelNumber() {
        return modelNumber;
    }
    @Override
    public String toString(){
        return String.format("%s %f", this.name,this.price);
    }

}
