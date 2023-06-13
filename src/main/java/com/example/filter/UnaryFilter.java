package com.example.filter;

public class UnaryFilter implements Filter {

    private final String columnName;
    private final UnaryOperation op;

    public UnaryFilter(String columnName, UnaryOperation op) {
        this.columnName = columnName;
        this.op = op;
    }


    @Override
    public String toParameter() {
        return op.getOp() + columnName;
    }
}
