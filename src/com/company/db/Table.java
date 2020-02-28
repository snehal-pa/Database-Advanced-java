package com.company.db;

import com.company.Annotations.Gender;
import com.company.Annotations.Positive;
import com.company.Annotations.Unique;

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

    public boolean insert(Object obj) {
        if (isValid(obj)) {
            data.add(obj);
            saveTable();
            return true;
        } else {
            return false;
        }
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
                            } else if (f.getType() == Character.TYPE) {
                                f.set(loadedPerson, loadedSplits[i].charAt(0));
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
        if (found.size() == 0) System.out.println("No match found");
        return found;
    }

    public void removeData(String title, Object val) {
        System.out.println("---------------------------------------------------------------------------------");
        Object[] objs = data.toArray();
        boolean found = false;
        for (int i = 0; i < objs.length; i++) {
            Field[] fields = objs[i].getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.getName().equalsIgnoreCase(title) && field.get(objs[i]).toString().equalsIgnoreCase(val.toString())) {
                        System.out.println("Removed from " + this.name +" table-------: " + writeRow(objs[i]));
                        found = true;
                        data.remove(objs[i]);
                        saveTable();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        if(!found) System.out.println("No such data found to remove");

    }

    public void update(String title, Object val1, Object replaceTo) {
        System.out.println("\n-----------------------------------------------------------------------------");
        //System.out.println(writeTitles(fieldsName));
        //List<Object> found = new ArrayList<>();
        for (Object obj : data) {
            try {
                Field[] fields = klass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equalsIgnoreCase(title) && field.get(obj).toString().equalsIgnoreCase(val1.toString())) {
                        field.set(obj, replaceTo);
                        //found.add(obj);
                        System.out.println(field.getName() + " = " + val1 + " is updated " + replaceTo);
                        System.out.println("\nupdated----- " + writeRow(obj));
                        saveTable();
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    //overloaded
    public void update(String title1, Object val1, String title2, Object replaceTo) {
        System.out.println("\n-----------------------------------------------------------------------------");
        boolean found = false;

        for (Object obj : data) {
            try {
                Field[] fields = klass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equalsIgnoreCase(title1) && field.get(obj).toString().equalsIgnoreCase(val1.toString())) {
                        for (Field f : fields) {
                            if (f.getName().equalsIgnoreCase(title2)) {
                                found = true;
                                f.setAccessible(true);
                                Object oldValue = f.get(obj);
                                if (oldValue.toString().equalsIgnoreCase(replaceTo.toString())) {
                                    System.out.printf("\n%s is already %s\n", f.getName().toUpperCase(), oldValue);
                                } else {
                                    f.set(obj, replaceTo);
                                    if (isPositive(obj) && isCorrectGenderName(obj)) {
                                        System.out.println(f.getName().toUpperCase() + " = " + oldValue + " is updated to " + replaceTo);
                                        System.out.println("\n updated----- " + writeRow(obj));
                                        saveTable();
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (!found) System.out.println("No match found, Check the column-name or value");
    }

    private boolean isValid(Object obj) {
        return isUnique(obj) && isPositive(obj) && isCorrectGenderName(obj);
    }


    private boolean isUnique(Object obj/*, Field[] fields*/) {
        Field[] fields = klass.getDeclaredFields();

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
                                    System.out.println(field.getName() + " " + field.get(obj) + "  should be unique");
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

    private boolean isPositive(Object obj) {
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
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return valid;

    }

    private boolean isCorrectGenderName(Object obj) {
        Field[] fields = klass.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            Gender gender = f.getAnnotation(Gender.class);
            if (gender != null) {
                try {
                    if (f.get(obj).toString().equalsIgnoreCase("f")
                            || f.get(obj).toString().equalsIgnoreCase("m")
                            || f.get(obj).toString().equalsIgnoreCase("o")) {
                        return true;
                    } else {
                        System.out.println("type......\nm/M  for Male\nf/F  for Female\no/O  for Other ");
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * This method uses the class Row that is no longer in use
     *
     * @Deprecated
     */
    public boolean insert(Row row) {
        if (!data.contains(row) && row.size() == fieldsName.size()) {
            data.add(row);
            //saveTable();
            return true;
        }
        return false;
    }
}



