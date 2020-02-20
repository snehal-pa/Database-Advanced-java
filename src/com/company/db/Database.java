package com.company.db;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static String folderName;
    private String name;

    private List<Table> tables;


    public Database(String name) {
        this.name = name;
        tables = new ArrayList<>();
        folderName = this.name + ".db";
       // makeFolder();
    }

    public String getDbName() {
        return name;
}

    public static String getFolderName() {
        return folderName;
    }

//    public Table getTable(Class klass) {
//        if (!tables.containsKey(klass.getSimpleName())) return null;
//        else return tables.get(klass.getSimpleName());
//    }

    public void addTable(Table table) {
        tables.add( table);
        table.saveTable();

    }

    public List<Table> getTables() {
        return tables;
    }
    @Deprecated
    public void makeFolder() {
        File folder = new File(folderName);
        if (!folder.exists())
            folder.mkdir();
        try {
            folder.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
