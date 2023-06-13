package com.example.filter;

public enum BinaryOperation {
    LessThan("%3C"),
    LessThenOrEqual("<="),
    GreaterThan(">"),
    GreaterThanOrEqual(">="),
    Match("="),
    NegateMatch("!="),
    Include("="),
    RegexEqual("=");

    private final String s;

    BinaryOperation(String s) {
        this.s = s;
    }

    String getOp() {
        return s;
    }
}
