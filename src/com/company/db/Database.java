package com.company.db;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    private String name;

    private List<Table> tables;


    public Database(String name) {
        this.name = name;
        tables = new ArrayList<>();
    }

    public String getDbName() {
        return name;
}


    public void addTable(Table table) {
        tables.add( table);
        table.saveTable();

    }

    public List<Table> getTables() {
        return tables;
    }
}
