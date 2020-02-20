package com.company.app;

import com.company.db.Database;
import com.company.db.Table;

public class Program {
    Database db = new Database("webshop");

    //Table productTable = new Table(Product.class);
    Table personTable = new Table(Person.class);



    public void run() {
        db.addTable(personTable);
        //db.addTable(productTable);


       //productTable.insert(new Product("Teddy Bear", 199.9f));

        personTable.insert(new Person("Aarav","22","m"));




//        productTable.find("Name", "toy Horse");
//        personTable.find("age", "30");
//        productTable.find("price", 199.9);
//
       // personTable.removeData("gender", "f");
        personTable.update("name", "sahana","Geet");
        //personTable.update("personnumber", "1942339614", "name", "mani");
//        personTable.removeData("personnumber", 1362476163);
    }


}
