package com.zergatstage.seminar02.task01.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Dog extends Animal{

    public Dog(String name, int age) {
        super(name, age);
    }


    private int weight;

//    @Override
//    public void makeSound() {
//        System.out.println("Bark!");
//    }

    public void fetchTheStick(){
        System.out.printf("\nThe %s retrieve the stick", this.getName());
    }

}
