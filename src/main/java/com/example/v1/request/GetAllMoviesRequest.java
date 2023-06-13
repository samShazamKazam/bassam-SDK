package com.example.v1.request;

import com.example.client.LotrClient;
import com.example.client.LotrClientImpl;
import com.example.v1.response.HttpResponse;
import com.example.v1.response.LotrMovieResponse;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

public class GetAllMoviesRequest extends Request {

    public static final String MOVIE_PATH = "/movie";
    public GetAllMoviesRequest() {
        super();
    }

    @Override
    public String getPath() {
        return MOVIE_PATH;
    }
}
