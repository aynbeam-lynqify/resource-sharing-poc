package com.example.filesharing.demo.costing.types;

public enum Expression {
    NONE(""),
    MINUS("-"),
    PLUS("+"),
    DIVIDE("/"),
    MULTIPLY("*");

    private final String symbol;

    Expression(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }
}