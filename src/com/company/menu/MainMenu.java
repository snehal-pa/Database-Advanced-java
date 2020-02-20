package com.company.menu;

import com.company.app.Person;
import com.company.app.Product;
import com.company.db.Database;
import com.company.db.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainMenu extends Menu {

    private ArrayList<MenuChoice> mainmenu = new ArrayList<>();
    private ArrayList<MenuChoice> tableChoiceMenu = new ArrayList<>();

    Database db = new Database("webshop");
    Table productTable = new Table(Product.class);
    Table personTable = new Table(Person.class);
    List<Table> tables;


    public MainMenu() {
        db.addTable(personTable);
        db.addTable(productTable);
        tables = db.getTables();
        mainmenu.add(new MenuChoice("Insert data", 1, this::insert));
        mainmenu.add(new MenuChoice("Find data", 2, this::find));

    }

    private void find(Object o) {
        for (int i = 0; i < tables.size(); i++) {
            tableChoiceMenu.add(new MenuChoice(tables.get(i).getName(), i, this::findFrom, tables.get(i)));
        }

    }

    private void findFrom(Object o) {
        Table table = (Table) o;
        Field[] fields = table.getKlass().getDeclaredFields();
        Scanner input = new Scanner(System.in);
        for(Field field: fields){
            System.out.println("Enter" + field.getName());
            Object obj = input.next();

        }
    }


    private void insert(Object o) {
        for (int i = 0; i < tables.size(); i++) {
            tableChoiceMenu.add(new MenuChoice(tables.get(i).getName(), i, this::insertIn, tables.get(i)));
        }

    }

    private void insertIn(Object o) {
    }



}
