package com.example.v1.response;

import com.example.v1.model.Movie;
import com.google.gson.Gson;

import java.util.List;

public class LotrMovieResponse {
    private List<Movie> docs;
    private int total;
    private int limit;
    private int offset;
    private int page;
    private int pages;


    public LotrMovieResponse(List<Movie> fromJson) {
        docs = fromJson;
    }


    // Getters and Setters

    public List<Movie> getDocs() {
        return docs;
    }

    public void setDocs(List<Movie> docs) {
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
