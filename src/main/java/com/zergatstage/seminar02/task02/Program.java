package com.zergatstage.seminar02.task02;

import java.util.UUID;

public class Program {

    /*
    Задача 2: Реализовать простой фреймворк для создания SQL-запросов на основе Java объектов

    Фреймворк должен позволять аннотировать классы и поля для связывания их
    с таблицами и столбцами в базе данных.

    1. Аннотации для маппинга:
        Создайте аннотации, такие как @Table, @Column для маппинга классов,
        таблиц и полей в базе данных.

    2. Механизм генерации SQL-запросов:
        Реализуйте класс QueryBuilder, который может принимать объект и генерировать
        SQL-запросы для выполнения операций CRUD (Create, Read, Update, Delete) на основе аннотаций.
        Используйте Reflection для получения метаданных класса,
        аннотированного объекта, чтобы построить соответствующий SQL-запрос.

    3. Пример использования:
        Создайте простой класс, аннотированный для маппинга с базой данных.
        Используйте ваш фреймворк для генерации SQL-запросов для различных операций,
        таких как вставка, выборка, обновление и удаление.
    */

    public static void main(String[] args) throws IllegalAccessException {

        Employee user = new Employee("Stanislav", "sample@gmail.com");
        UUID pk = UUID.randomUUID();

        QueryBuilder queryBuilder = new QueryBuilder();

        // Генерация SQL-запроса для вставки
        String insertQuery = queryBuilder.buildInsertQuery(user);
        System.out.println("Insert Query: " + insertQuery);


        // Генерация SQL-запроса для выборки
        String selectQuery = queryBuilder.buildSelectQuery(Employee.class, pk);
        System.out.println("Select Query: " + selectQuery);

        user.setEmail("test@gmail.com");

        // Генерация SQL-запроса для обновления
        String updateQuery = queryBuilder.buildUpdateQuery(user);
        System.out.println("Update Query: " + updateQuery);

        //SQL delete
        String deleteQuery = queryBuilder.buildDeleteQuery(Employee.class, pk);
        System.out.println("Delete Query: " + deleteQuery);

    }

}
