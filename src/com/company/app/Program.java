package com.company.app;

import com.company.db.Database;
import com.company.db.Table;

public class Program {
    Database db = new Database("webshop");

    Table productTable = new Table(Product.class);
    Table personTable = new Table(Person.class);


    public void run() {
        db.addTable(personTable);
        db.addTable(productTable);


        productTable.insert(new Product(32020975, "ball", 119.9f));
        personTable.insert(new Person("Shaan", 30, "M"));


        productTable.find("Name", "barbie");
        personTable.find("gender", "F");

        //update name from shaan to Riaan
        personTable.update("name", "shaan", "Riaan");

        //find a person by personnumber and update his/her name,age etc...
        personTable.update("personnumber", "1896368203", "name", "Sahana");


        productTable.removeData("modelnumber", "32020925");
       personTable.removeData("personnumber", 1381355546);


    }


}
