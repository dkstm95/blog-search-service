package com.example.blogsearch.external.exception;

import lombok.Getter;

@Getter
public class ExternalSearchServiceException extends RuntimeException {

    private final String message;

    public ExternalSearchServiceException(String message) {
        super(message);
        this.message = message;
    }

}
