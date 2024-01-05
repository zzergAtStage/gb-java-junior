package com.zergatstage.lesson01;

@FunctionalInterface
public interface CreditRating {
    double calcCreditRating(Registry registry, int someParameter);
}
