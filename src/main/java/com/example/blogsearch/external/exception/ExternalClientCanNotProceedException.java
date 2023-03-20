package com.example.blogsearch.external.exception;

public class ExternalClientCanNotProceedException extends RuntimeException {

    private final String message;
    private final int statusCode;

    public ExternalClientCanNotProceedException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatusCode() { return this.statusCode; }

}
