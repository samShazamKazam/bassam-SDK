package com.example.v1.model;

public class SortOption {
    String columnName;
    Sort.Direction direction;

    public SortOption(String columnName, Sort.Direction direction) {
        this.columnName = columnName;
        this.direction = direction;
    }

    public String getColumnName() {
        return columnName;
    }

    public Sort.Direction getDirection() {
        return direction;
    }
}
