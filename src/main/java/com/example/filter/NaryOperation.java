package com.example.filter;

public enum NaryOperation {
    Include("="),
    Exclude("!=");

    private final String s;

    NaryOperation(String s) {
        this.s = s;
    }

    String getOp() {
        return s;
    }
}
