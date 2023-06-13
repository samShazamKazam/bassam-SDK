package com.example.v1.response;

import com.example.v1.model.Quote;
import com.google.gson.Gson;

import java.util.List;
import java.util.Optional;

public class LotrQuoteResponse {
    private List<Quote> docs;
    private int total;
    private int limit;
    private int offset;
    private int page;
    private int pages;

    public LotrQuoteResponse(List<Quote> fromJson) {
        docs = fromJson;
    }


    // Getters and Setters

    public List<Quote> getDocs() {
        return docs;
    }

    public void setDocs(List<Quote> docs) {
        this.docs = docs;
    }

    public static LotrMovieResponse fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, LotrMovieResponse.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

