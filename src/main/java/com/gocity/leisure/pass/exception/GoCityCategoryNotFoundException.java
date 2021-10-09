package com.gocity.leisure.pass.exception;

public class GoCityCategoryNotFoundException extends RuntimeException {
    private static final String CATEGORY_NOT_FOUND = "Category not found";

    public GoCityCategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND);
    }
}
