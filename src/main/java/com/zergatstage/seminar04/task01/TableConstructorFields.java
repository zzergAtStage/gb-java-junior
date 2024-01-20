package com.zergatstage.seminar04.task01;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TableConstructorFields {
    private String columnName;
    private String columnDataType;
    private int columnSize;
    private String options;

    @Override
    public String toString() {
        return "TableConstructorFields{" +
                "columnName='" + columnName + '\'' +
                ", columnDataType='" + columnDataType + '\'' +
                ", columnSize=" + columnSize +
                ", options='" + options + '\'' +
                '}';
    }
}
