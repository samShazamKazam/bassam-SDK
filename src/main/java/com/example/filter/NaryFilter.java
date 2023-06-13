package com.example.filter;

import java.util.List;

public class NaryFilter implements Filter {

    private final String columnName;
    private final NaryOperation op;
    private final List<String> value;

    public NaryFilter(String columnName, NaryOperation op, List<String> value) {
        this.columnName = columnName;
        this.op = op;
        this.value = value;
    }

    @Override
    public String toParameter() {
        return columnName + op.getOp() + String.join(",", value);
    }
}
