package com.example.v1.response;

import java.util.Optional;

public class ResponseFactory {
    public static <T> HttpResponse<T> createResponse(T fromJson, int code) {
        return new HttpResponse<T>(Optional.of(fromJson), code, Optional.empty());
    }

    public static <T> HttpResponse<T> createErrorResponse(int code, Optional<String> errorMsg) {
        return new HttpResponse<T>(Optional.empty(), code, Optional.empty());
    }
}
