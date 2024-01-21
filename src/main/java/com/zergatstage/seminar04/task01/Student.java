package com.zergatstage.seminar04.task01;

import jakarta.persistence.*;

import java.util.Random;

/**
 * Random objects class. Can create a predefined object or create a random object
 */
@Entity
@Table(name = "students")
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student(String name, int age) {
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
