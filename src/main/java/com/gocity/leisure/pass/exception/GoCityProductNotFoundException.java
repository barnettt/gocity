package com.gocity.leisure.pass.exception;

public class GoCityProductNotFoundException extends RuntimeException {
    private static final String NOT_FOUND_MSG = "product/products not found";

    public GoCityProductNotFoundException() {
        super(NOT_FOUND_MSG);
    }

}
