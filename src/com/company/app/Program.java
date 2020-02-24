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


      productTable.insert(new Product(5, "Teddy-bear", 100.f));

      //  personTable.insert(new Person("vihaan",30,"m"));
//        personTable.insert(new Person("Sara", 27,"f"));

//        productTable.find("Name", "toy Horse");
     //   personTable.find("Name", "vihaan" );

        //personTable.update("name", "jash","riaan");
      //  personTable.update("personnumber", "1018550543", "name", "Rihaan");

        //personTable.removeData("personnumber", "1677779320");
    }


}
