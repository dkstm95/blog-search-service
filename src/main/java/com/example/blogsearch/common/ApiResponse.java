package com.example.blogsearch.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ApiResponse {

    private final int code;
    private final String message;
    private final Object data;

    public static <T> ApiResponse success(T data) {
        return ApiResponse.builder()
                .code(ApiStatus.OK.getCode())
                .message(ApiStatus.OK.getMessage())
                .data(data)
                .build();
    }

    public static ApiResponse badRequest(List<String> errorMessages) {
        return ApiResponse.builder()
                .code(ApiStatus.BAD_REQUEST.getCode())
                .message(ApiStatus.BAD_REQUEST.getMessage())
                .data(errorMessages)
                .build();
    }

    public static ApiResponse badRequest(String errorMessage) {
        return ApiResponse.builder()
                .code(ApiStatus.BAD_REQUEST.getCode())
                .message(ApiStatus.BAD_REQUEST.getMessage())
                .data(errorMessage)
                .build();
    }

    public static ApiResponse internalServerError(String errorMessage) {
        return ApiResponse.builder()
                .code(ApiStatus.INTERNAL_SERVER_ERROR.getCode())
                .message(ApiStatus.INTERNAL_SERVER_ERROR.getMessage())
                .data(errorMessage)
                .build();
    }

}
