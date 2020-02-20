package com.company.db;

import java.util.ArrayList;
import java.util.List;

public class DataRow {
    private List<Object> data = new ArrayList<>();

    private int id;

    public DataRow(Object... values) {
        for (Object o : values) {
            data.add(o);
        }
    }

    public int size() {
        return data.size();
    }

    public List<Object> getDataList() {
        return data;
    }


    public Object getData(int i) {
        if (i < 0 || i > data.size()) {
            return null;
        } else {
            return data.get(i);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String result = Integer.toString(id) + ", ";
        for (int i = 0; i < data.size(); i++) {
            result += data.get(i) + ", ";
        }
        return result.substring(0, result.length() - 2);
    }
}

