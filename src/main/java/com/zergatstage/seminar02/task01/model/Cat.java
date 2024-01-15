package com.zergatstage.seminar02.task01.model;

public class Cat  extends Animal{
    private double tailLength;

    public Cat(String name, int age) {
        super(name, age);
    }

    public void makeSound(){
        System.out.println("Meow");
    }
}
