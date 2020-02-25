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


     //productTable.insert(new Product(33620915, "Toy horse", 499.9f));
      personTable.insert(new Person("Shan",28,"male"));


        //productTable.find("Name", "teddy-bear");
        //personTable.find("gender", "F" );


        //personTable.update("name", "jash","riaan");
        //productTable.update("name","teddy-bear","Teddy bear");
        //personTable.update("personnumber", "1759007534", "name", "shahi");


        //productTable.removeData("modelnumber", "32620915");
        //personTable.removeData("personnumber", 1547498126);
    }


}
