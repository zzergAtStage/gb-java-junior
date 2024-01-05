package com.zergatstage.seminar01;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class App {
    public static void main(String[] args) {


        getAverageEvens(Arrays.asList(22,32,1,5,22,5,17,2342));
    }

    /**
     * Task 01. Calculates the average value of even numbers of the List
     * @param listNumbs Incoming list of values
     * @return Result of calculation
     */
    public static OptionalDouble getAverageEvens(List<Integer> listNumbs) {
        return listNumbs.stream()
                    .filter( n -> n % 2 == 0)
                    .mapToInt(Integer::intValue)
                    .average();
    }
}
