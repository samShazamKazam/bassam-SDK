package com.example.v1.request;

import com.example.client.LotrClient;
import com.example.client.LotrClientImpl;
import com.example.filter.*;
import com.example.v1.model.Sort;
import com.example.v1.model.SortOption;
import com.example.v1.response.HttpResponse;
import com.example.v1.response.LotrMovieResponse;
import com.example.v1.response.ResponseFactory;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Request<T> {
    private Optional<Integer> limit = Optional.empty();
    private Optional<Integer> page = Optional.empty();;
    private Optional<Integer> offset = Optional.empty();;
    private Optional<SortOption> sortOption = Optional.empty();

    private List<Filter> filters = new ArrayList<>();

    private String token = "";

    public Request authToken(String token) {
        this.token = token;
        return this;
    }

    public String getAuthToken() {
        return token;
    }

    public Request limit(int limit) {
        this.limit = Optional.of(limit);
        return this;
    }

    public Request page(int page) {
        this.page = Optional.of(page);
        return this;
    }

    public Request offset(int offset) {
        this.offset = Optional.of(offset);
        return this;
    }

    public Request sortBy(String column_name, Sort.Direction dir) {
        sortOption = Optional.of(new SortOption(column_name, dir));
        return this;
    }

    abstract public String getPath();

    public String toUrl() {
        List<String> list = new ArrayList<>();
        limit.ifPresent(l -> list.add("limit=" + l));
        offset.ifPresent(o -> list.add("offset=" + o));
        page.ifPresent(p -> list.add("page=" + p));
        sortOption.ifPresent(sort -> list.add("sort=" + sort.getColumnName() + ":" + sort.getDirection().toString().toLowerCase()));

        filters.stream().forEach(f -> list.add(f.toParameter()));

        return list.isEmpty() ? getPath() :
                getPath() + "?" + String.join("&", list);
    }

    public Request filter(String column_name, BinaryOperation op, String value) {
        filters.add(new BinaryFilter(column_name, op, value));
        return this;
    }

    public Request filter(String column_name, NaryOperation op, List<String> value) {
        filters.add(new NaryFilter(column_name, op, value));
        return this;
    }

    public Request filter(String column_name, UnaryOperation op) {
        filters.add(new UnaryFilter(column_name, op));
        return this;
    }


    <T> HttpResponse<T> callGetEndpoint(String base_url, Request req, Type clazz) throws IOException {
        final HttpGet httpGet = new HttpGet(base_url + req.toUrl());

        if (!req.getAuthToken().isBlank())
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + req.getAuthToken());

        Gson gson = new Gson();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build()) {
            HttpResponse<T> quotes = httpClient.execute(httpGet, response ->
                    processResponse(clazz, gson, response)
            );
            return quotes;
        }
    }

    private static <T> HttpResponse<T> processResponse(Type clazz, Gson gson, ClassicHttpResponse response) throws IOException, ParseException, JsonSyntaxException {
        if (response.getCode() >= 200 && response.getCode() <= 399) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            T res = gson.fromJson(jsonResponse, clazz);

            return ResponseFactory.createResponse(res, response.getCode());
        } else {
            Optional<String> errorMsg = Optional.of(EntityUtils.toString(response.getEntity()));
            return ResponseFactory.createErrorResponse(response.getCode(), errorMsg);
        }
    }
}
