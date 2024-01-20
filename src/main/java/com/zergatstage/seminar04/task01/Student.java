package com.zergatstage.seminar04.task01;

import java.util.Random;

/**
 * Random objects class. Can create a predefined object or create a random object
 */
public class Student {

    //factory attributes
    private static final String[] randomFirstNames = new String[] {"Alice", "Bob", "Charlie", "David", "Emily", "Frank"
            , "Grace", "Henry", "Iris", "Jack", "Kelly", "Leo", "Mia", "Noah", "Olivia"
            , "Ryan", "Sophia", "Tyler", "Zoe", "Adam"};

    private static final String[] randomLastNames = new String[]{"Smith", "Jones", "Lee", "Chen", "Brown", "Wilson", "Liu"
            , "Miller", "Wang", "Taylor", "Johnson", "Clark", "Davis", "Martin", "Thompson"
            , "Anderson", "White", "Evans", "Williams", "Lewis"};

    private static final Random random = new Random();

    //class attributes
    private int id;
    private String Name;
    private int age;

    public Student(int id, String name, int age) {
        this.id = id;
        Name = name;
        this.age = age;
    }

    public Student(String name, int age) {
        Name = name;
        this.age = age;
    }

    //factory method
    public static Student create(){
        return new Student(randomFirstNames[random.nextInt(randomFirstNames.length)] + " " +
                randomLastNames[random.nextInt(randomLastNames.length)], random.nextInt(18,26) );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
