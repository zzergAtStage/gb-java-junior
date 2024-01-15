package com.zergatstage.seminar02.task01;

import com.zergatstage.seminar02.task01.model.Animal;
import com.zergatstage.seminar02.task01.model.Cat;
import com.zergatstage.seminar02.task01.model.Dog;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        System.out.println("=============== reflection ====================");
        Animal dog = new Dog("Felix", 4);
        Animal cat = new Cat("Sheba", 8);

        Animal[] animals = {dog, cat};
        for (Animal animalItem: animals){
            System.out.println(animalItem.getName() + " : " + animalItem);
            Method[] methods = animalItem.getClass().getDeclaredMethods();
            Optional<Method> makeSoundMethod = Arrays.stream(methods)
                    .filter(method -> method.getName().equalsIgnoreCase("makeSound"))
                    .findFirst();
            makeSoundMethod.ifPresent(method -> {
                try {
                    method.invoke(animalItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }
    }
}
