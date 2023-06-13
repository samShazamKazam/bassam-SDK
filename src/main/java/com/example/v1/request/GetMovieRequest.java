package com.example.v1.request;

import com.example.client.LotrClient;
import com.example.v1.response.HttpResponse;
import com.example.v1.response.LotrMovieResponse;

import java.io.IOException;

public class GetMovieRequest extends Request {

    public static final String MOVIE_PATH = "/movie";
    private final String movieId;

    public GetMovieRequest(String movieId) {
        super();
        this.movieId = movieId;
    }

    @Override
    public String getPath() {
        return MOVIE_PATH + "/" + movieId;
    }
}
