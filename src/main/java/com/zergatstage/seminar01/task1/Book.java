package com.zergatstage.seminar01.task1;

import lombok.Getter;

import java.util.Locale;

/**
 * Книга
 */
@Getter
public class Book {

    //region Конструкторы

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    //endregion

    //region Методы

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Книга:\n\tНаименование: %s\n\tАвтор: %s\n\tГод издания: %s\n",
                title, author, year);
    }

    //endregion

    //region Поля

    /**
     * Наименование
     */
    private final String title;

    /**
     * Автор
     */
    private final String author;

    /**
     * Год издания
     */
    private final int year;

    //endregion
}
