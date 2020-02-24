package com.company.db;

import com.company.app.Positive;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private List<Object> data = new ArrayList<>();
    private List<String> fieldsName = new ArrayList<>();

    private Class klass;
    private String fileName;
    private File file;

    public Table(Class klass) {
        this.klass = klass;
        this.name = klass.getSimpleName();
        titles(klass.getDeclaredFields());
        fileName = "src\\tables\\ " + this.name + ".table";
        file = new File(fileName);
        loadTable();

    }

    private boolean isUnique(Object obj, Field[] fields) {
        for (Field field : fields) {
            field.setAccessible(true);
            Unique unique = field.getAnnotation(Unique.class);
            if (unique != null) {
                for (Object o : this.data) {
                    Field[] fieldsOfOtherObjects = fields;
                    for (Field f : fieldsOfOtherObjects) {
                        Unique unique1 = f.getAnnotation(Unique.class);
                        if (unique1 != null) {
                            try {
                                if (f.get(o).toString().equals(field.get(obj).toString())) {
                                    System.out.println(field.getName()+ " " + field.get(obj)+"  should be unique" );
                                    return false;
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean insert(Object obj) {
        Field[] fields = klass.getDeclaredFields();
        boolean valid = true;
        for (Field f : fields) {
            f.setAccessible(true);
            Positive a = f.getAnnotation(Positive.class);
            if (a != null) {
                try {
                    if (f.getDouble(obj) < 0.0) {
                        Object oldValue = f.get(obj);
                        valid = false;
                        System.out.println(f.getName() + " can't be negative " + oldValue.toString());
                    }
                    //System.out.println("getdouble NOT < 0: " + f.getName() + " " + f.get(obj).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (isUnique(obj, fields) && valid) {
            data.add(obj);
            saveTable();
            return true;
        } else {
            return false;
        }
    }


    @Deprecated
    public boolean insert(DataRow dataRow) {
        if (!data.contains(dataRow) && dataRow.size() == fieldsName.size()) {
            // dataRow.setId(Id++);
            data.add(dataRow);
            //saveTable();
            return true;
        }
        return false;
    }

    public void titles(Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            this.fieldsName.add(fields[i].getName());
        }
    }

    public int numRows() {
        return data.size();
    }

    public void loadTable() {
        String fileName = "src\\tables\\ " + this.name + ".table";
        BufferedReader br;
        String line;
        Object loadedPerson = null;
        if (file.exists()) {
            try {
                br = new BufferedReader(new FileReader(fileName));
                String titles = br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] loadedSplits = line.split(", ");
                    loadedPerson = klass.getConstructor().newInstance();
                    for (int i = 0; i < fieldsName.size(); i++) {
                        try {
                            Field f = klass.getDeclaredField(fieldsName.get(i));
                            f.setAccessible(true);
                            if (f.getType() == Integer.TYPE) {
                                f.set(loadedPerson, Integer.parseInt(loadedSplits[i]));
                            } else if (f.getType() == Long.TYPE) {
                                f.set(loadedPerson, Long.parseLong(loadedSplits[i]));
                            } else if (f.getType() == Float.TYPE) {
                                f.set(loadedPerson, Float.parseFloat(loadedSplits[i]));
                            } else if (f.getType() == Double.TYPE) {
                                f.set(loadedPerson, Double.parseDouble(loadedSplits[i]));
                            } else
                                f.set(loadedPerson, loadedSplits[i]);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    this.insert(loadedPerson);
                }
            } catch (InstantiationException | IllegalAccessException | IOException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    private String writeTitles(List<String> titles) {
        String result = "";
        for (int i = 0; i < titles.size(); i++) {
            result += titles.get(i) + ", ";
        }
        return result.substring(0, result.length() - 2);

    }

    private String writeRow(Object obj) {
        Field[] fields = klass.getDeclaredFields();

        String result = "";
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                result += fields[i].get(obj) + ", ";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result.substring(0, result.length() - 2);
    }

    public void saveTable() {
        PrintStream output = null;
        try {
            output = new PrintStream(fileName);
            if (fieldsName.size() != 0)
                output.println(writeTitles(fieldsName));
            for (Object obj : data) {
                output.println(writeRow(obj));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }


    public int numColumns() {
        return fieldsName.size();
    }

    public String getColumnTitle(int k) {
        return fieldsName.get(k);
    }

    public String getName() {
        return this.name;
    }

    public Class getKlass() {
        return klass;
    }

    public int findColNum(String name) {
        for (int i = 0; i < fieldsName.size(); i++) {
            if (fieldsName.get(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public List<Object> find(String title, Object val) {
        System.out.println("----------------------------------------------------------------");
        System.out.println(writeTitles(fieldsName));
        List<Object> found = new ArrayList<>();
        for (Object obj : data) {
            try {
                Field[] fields = klass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    //if(field.getName().equalsIgnoreCase(title) && field.get(obj).toString().contains(val.toString())){
                    if (field.getName().equalsIgnoreCase(title) && field.get(obj).toString().equalsIgnoreCase(val.toString())) {
                        found.add(obj);
                        System.out.println(writeRow(obj));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return found;
    }

    public void removeData(String title, Object val) {
        System.out.println("---------------------------------------------------------------------------------");
        Object[] objs = data.toArray();
        for (int i = 0; i < objs.length; i++) {
            Field[] fields = objs[i].getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.getName().equalsIgnoreCase(title) && field.get(objs[i]).toString().equalsIgnoreCase(val.toString())) {
                        System.out.println("Removed from table-------: " + writeRow(objs[i]));

                        data.remove(objs[i]);
                        saveTable();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    public void update(String title, Object val1, Object toReplace) {
        System.out.println(writeTitles(fieldsName));
        //List<Object> found = new ArrayList<>();
        for (Object obj : data) {
            try {
                Field[] fields = klass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equalsIgnoreCase(title) && field.get(obj).toString().equalsIgnoreCase(val1.toString())) {
                        field.set(obj, toReplace);
                        //found.add(obj);
                        System.out.println(field.getName() + " = " + val1 + " is updated " + toReplace);
                        System.out.println("\n updated----- " + writeRow(obj));
                        saveTable();
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //overloaded
    public void update(String title1, Object val1, String title2, Object toReplace) {
        System.out.println("-----------------------------------------------------------------------------");
        for (Object obj : data) {
            try {
                Field[] fields = klass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equalsIgnoreCase(title1) && field.get(obj).toString().equalsIgnoreCase(val1.toString())) {
                        for (Field f : fields) {
                            if (f.getName().equalsIgnoreCase(title2)) {
                                f.setAccessible(true);
                                Object oldValue = f.get(obj);
                                if (oldValue.toString().equalsIgnoreCase(toReplace.toString())) {
                                    System.out.printf("\n%s = %s is already there", f.getName(), oldValue);
                                } else {
                                    f.set(obj, toReplace);
                                    System.out.println(f.getName() + " = " + oldValue + " is updated to " + toReplace);
                                    System.out.println("\n updated----- " + writeRow(obj));
                                    saveTable();
                                }
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}



