package com.example.blogsearch.external.exception;

public class ExternalClientCanNotProceedException extends RuntimeException {

    private final String message;

    public ExternalClientCanNotProceedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
