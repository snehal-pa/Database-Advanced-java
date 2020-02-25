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

     //productTable.insert(new Product(33620925, "Lego", 499.9f));
     // personTable.insert(new Person("Shan",30,"M"));


        //productTable.find("Name", "barbie");
        //personTable.find("gender", "F" );


        //personTable.update("name", "jash","riaan");
        //personTable.update("personnumber", "1778189720", "gender", "M");


        //productTable.removeData("modelnumber", "33620915");
        //personTable.removeData("personnumber", 1778189720);
    }


}
