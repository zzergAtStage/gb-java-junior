package com.zergatstage.seminar01.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Optional;
import java.util.function.Consumer;


/**
 * Корзина
 * @param <T> Еда
 */
public class Cart<T extends Food> {

    //region Поля

    /**
     * Товары в магазине
     */
    private final List<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    //endregion

    //region Конструкторы

    /**
     * Создание нового экземпляра корзины
     * @param market принадлежность к магазину
     */
    public Cart(Class<T> clazz, UMarket market)
    {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    public void cardBalancing()
    {
        boolean proteins = false;
        boolean fats = false;
        boolean carbohydrates = false;
        //checks for the current state of the cart
        for (var food : foodstuffs)
        {
            if (!proteins && food.getProteins())
                proteins = true;
            else
            if (!fats && food.getFats())
                fats = true;
            else
            if (!carbohydrates && food.getCarbohydrates())
                carbohydrates = true;
            if (proteins && fats && carbohydrates)
                break;
        }

        if (proteins && fats && carbohydrates)
        {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        for (var thing : market.getThings(clazz))
        {
            if (!proteins && thing.getProteins())
            { //if the is no proteins thing - add the first found in the collection above
                proteins = true;
                foodstuffs.add(thing);
            }
            else if (!fats && thing.getFats())
            {
                fats = true;
                foodstuffs.add(thing);
            }
            else if (!carbohydrates && thing.getCarbohydrates())
            {
                carbohydrates = true;
                foodstuffs.add(thing);
            }
            if (proteins && fats && carbohydrates)
                break;
        }

        if (proteins && fats && carbohydrates)
            System.out.println("Корзина сбалансирована по БЖУ.");
        else
            System.out.println("Невозможно сбалансировать корзину по БЖУ.");

    }

    //endregion


    //TODO: represent Cart with stream API

    public void cardBalancingv2() {
        Optional<T> proteinFood =  foodstuffs.stream().filter(Food::getProteins).findFirst();
        Optional<T> fatFood =  foodstuffs.stream().filter(Food::getFats).findFirst();
        Optional<T> carbFood = foodstuffs.stream().filter(Food::getCarbohydrates).findFirst();

        if (proteinFood.isPresent() && fatFood.isPresent() && carbFood.isPresent()) {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        if (!proteinFood.isPresent()) {
            proteinFood =  market.getThings(clazz).stream().filter(Food::getProteins).findFirst();
            proteinFood.ifPresent(foodstuffs::add);
        }
        if (!fatFood.isPresent()) {
            fatFood = market.getThings(clazz).stream().filter(Food::getFats).findFirst();
            fatFood.ifPresent(foodstuffs::add);
        }
        if (!carbFood.isPresent()) {
            carbFood = market.getThings(clazz).stream().filter(Food::getCarbohydrates).findFirst();
            carbFood.ifPresent(foodstuffs::add);
        }

        if (proteinFood.isPresent() && fatFood.isPresent() && carbFood.isPresent()) {
            System.out.println("Корзина сбалансирована по БЖУ.");
        } else {
            System.out.println("Невозможно сбалансировать корзину по БЖУ.");
        }
    }


    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }



    public void printFoodstuffs()
    {
        /*int index = 1;
        for (var food : foodstuffs)
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n", index++, food.getName(), food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет", food.getCarbohydrates() ? "Да" : "Нет");*/


        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                index.getAndIncrement(), food.getName(),
                food.getProteins() ? "Да" : "Нет",
                food.getFats() ? "Да" : "Нет",
                food.getCarbohydrates() ? "Да" : "Нет"));
    }
}
