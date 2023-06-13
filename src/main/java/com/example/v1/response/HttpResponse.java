package com.example.v1.response;

import java.util.Optional;

public class HttpResponse<T> {
    private Optional<T> body;

    private final int code;
    private final Optional<String> error;

    public HttpResponse(Optional<T> fromJson, int code, Optional<String> error) {
        body = fromJson;
        this.code = code;
        this.error = error;
    }

    public Optional<T> getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }

    public Optional<String> getError() {
        return Optional.empty();
    }
}
