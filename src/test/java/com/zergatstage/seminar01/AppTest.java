package com.zergatstage.seminar01;

import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    List<Integer> listNums;
    @BeforeEach
    void setUp(){
        listNums = Arrays.asList(22,32,1,5,22,5,17,2342);
    }
    @org.junit.jupiter.api.Test
    void getAverageEvens() {
        OptionalDouble newList = App.getAverageEvens(listNums);
        assertTrue(newList.isPresent());
        assertEquals(newList.getAsDouble(), 604.5d , 0.00000001);
    }
}