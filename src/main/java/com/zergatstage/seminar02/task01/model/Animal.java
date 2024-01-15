package com.zergatstage.seminar02.task01.model;

public class Animal {
    private String name;
    private int age;

    public void makeSound(){
        System.out.println("Animal makes sound");
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

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
