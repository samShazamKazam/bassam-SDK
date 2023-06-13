package com.example.filter;

public enum UnaryOperation {
    Include("="),
    Exist(""),
    NotExist("!");

    private final String s;

    UnaryOperation(String s) {
        this.s = s;
    }

    String getOp() {
        return s;
    }
}
