package com.gocity.leisure.pass.exception;

public class GoCityLastPurchaseDateException extends RuntimeException {
    private static final String DATE_BEFORE_ERROR = "Last purchase date cannot be before creation date.";
    public GoCityLastPurchaseDateException() {
        super(DATE_BEFORE_ERROR);
    }
}
