package com.company.db;

public class Column {
    private String name;

    int column;

    public Column(String name, Table table) {
        this.name = name;
        if (table.findColNum(name) != -1) {
            return;
        } else {
            System.out.println("Column not found");
        }
    }

    public String getName() {
        return name;
    }
}
