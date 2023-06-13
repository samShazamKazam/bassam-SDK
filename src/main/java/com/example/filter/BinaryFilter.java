package com.example.filter;

import java.beans.Encoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BinaryFilter implements Filter {

    private final String columnName;
    private final BinaryOperation op;
    private final String value;

    public BinaryFilter(String columnName, BinaryOperation op, String value) {
        this.columnName = columnName;
        this.op = op;
        this.value = value;
    }

    @Override
    public String toParameter() {
        return columnName + op.getOp() + value;
    }
}
