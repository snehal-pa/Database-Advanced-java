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

        //personTable.insert(new Person("Arvi","20","f"));

//        productTable.find("Name", "toy Horse");
        personTable.find("age", "25" );

        //personTable.update("name", "jash","riaan");
        //personTable.update("personnumber", "1837944459", "name", "Jash");

        //personTable.removeData("personnumber", "1677779320");
    }


}
