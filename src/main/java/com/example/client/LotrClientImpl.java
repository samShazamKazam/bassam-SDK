package com.example.client;

import com.example.v1.request.*;
import com.example.v1.response.HttpResponse;
import com.example.v1.response.LotrMovieResponse;
import com.example.v1.response.LotrQuoteResponse;
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
import java.util.Optional;

public class LotrClientImpl implements LotrClient {
    private String base_url = "https://the-one-api.dev/v2";

    LotrClientImpl(String base_url) {
        this.base_url = base_url;
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public HttpResponse<LotrMovieResponse> get(GetAllMoviesRequest request) throws IOException {
        Type typeToken = new TypeToken<LotrMovieResponse>() {}.getType();
        return callGetEndpoint(request, typeToken);
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public HttpResponse<LotrMovieResponse> get(GetMovieRequest request) throws IOException {
        Type typeToken = new TypeToken<LotrMovieResponse>() {}.getType();
        return callGetEndpoint(request, typeToken);
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public HttpResponse<LotrQuoteResponse> get(GetQuoteRequest request) throws IOException {
        Type typeToken = new TypeToken<LotrQuoteResponse>() {}.getType();
        return callGetEndpoint(request, typeToken);
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public HttpResponse<LotrQuoteResponse> get(GetAllQuotesRequest request) throws IOException {
        Type typeToken = new TypeToken<LotrQuoteResponse>() {}.getType();
        return callGetEndpoint(request, typeToken);
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public HttpResponse<LotrQuoteResponse> get(GetMovieQuotesRequest request) throws IOException {
        Type typeToken = new TypeToken<LotrQuoteResponse>() {}.getType();
        return callGetEndpoint(request, typeToken);
    }

    /**
     * @return
     */
    @Override
    public String getBaseUrl() {
        return base_url;
    }

    private <T> HttpResponse<T> callGetEndpoint(Request req, Type clazz) throws IOException {
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
