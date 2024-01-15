package com.zergatstage.seminar02.task02;

import java.lang.reflect.Field;
import java.util.UUID;

public class QueryBuilder {


    /**
     * Построить запрос на добавление данных в БД
     * @param obj
     * @return
     */
    public String buildInsertQuery(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        StringBuilder query = new StringBuilder("INSERT INTO ");

        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query
                    .append(tableAnnotation.name())
                    .append(" (");

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    query
                            .append(columnAnnotation.name())
                            .append(", ");
                }
            }

            if (query.charAt(query.length() - 2) == ',') {
                query.delete(query.length() - 2, query.length());
            }

            query.append(") VALUES (");

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    query
                        .append("'")
                        .append(field.get(obj))
                        .append("', ");
                }
            }

            if (query.charAt(query.length() - 2) == ',') {
                query.delete(query.length() - 2, query.length());
            }

            query.append(")");
        }
        else {
            return "";
        }
        return query.toString();
    }

    /**
     * Построить запрос на получение данных из БД
     * @param clazz
     * @param primaryKey
     * @return
     */
    public String buildSelectQuery(Class<?> clazz, UUID primaryKey){
        StringBuilder query = new StringBuilder("SELECT * FROM ");


        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query.append(tableAnnotation.name()).append(" WHERE ");

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.primaryKey()) {
                        query
                            .append(columnAnnotation.name())
                            .append(" = '").append(primaryKey).append("'");
                        break;
                    }

                }
            }

        }
        else {
            return "";
        }
        return query.toString();
    }

    /**
     * Построить запрос на update данных из бд
     * @param obj
     * @return
     */
    public String buildUpdateQuery(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        StringBuilder query = new StringBuilder("UPDATE ");

        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query.append(tableAnnotation.name()).append(" SET ");
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.primaryKey())
                        continue;
                    query.append(columnAnnotation.name()).append(" = '").append(field.get(obj)).append("', ");
                }
            }

            if (query.charAt(query.length() - 2) == ',') {
                query.delete(query.length() - 2, query.length());
            }

            query.append(" WHERE ");

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.primaryKey()) {
                        query.append(columnAnnotation.name()).append(" = '").append(field.get(obj)).append("'");
                        break;
                    }
                }
            }

        }
        else{
            return "";
        }

        return query.toString();

    }

    /**
     * TODO: Доработать запрос в рамках домашней работы
     * @param clazz
     * @param primaryKey
     * @return
     */
    public String buildDeleteQuery(Class<?> clazz, UUID primaryKey){
        StringBuilder query = new StringBuilder("DELETE FROM ");


        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query.append(tableAnnotation.name()).append(" WHERE ");

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.primaryKey()) {
                        query
                                .append(columnAnnotation.name())
                                .append(" = '").append(primaryKey).append("'");
                        break;
                    }

                }
            }

        }
        else {
            return "";
        }
        return query.toString();
    }





}
