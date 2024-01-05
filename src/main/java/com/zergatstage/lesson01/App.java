package com.zergatstage.lesson01;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        Registry someRegistry2 = new CreditRegistry();
        int someValue2 = 4;
        //implementation of interface
        CreditRating creditRating = (someRegistry,someValue) -> someRegistry.getAlgorithm() * someValue;

        //CreditRating creditRating1 = ;

        System.out.println(creditRating.calcCreditRating(someRegistry2,someValue2));

        //Stream API
        List<String> list;
        list = Arrays.asList("One", "Two", "Three", "Five");
        List<String> list2;
        list2 = list.stream()
                .filter( str -> str.equalsIgnoreCase("two"))
                .toList();
        System.out.println(Arrays.toString(list2.toArray()));
        list.stream()
                .filter( str -> str.equalsIgnoreCase("two")).forEach(System.out::println);

        //sorting in stream
        list.stream().sorted((o1, o2) -> (o1.length() < o2.length()) ? 1 : -1 ).forEach(System.out::println);

    }
}
