package com.example.v1.request;

public class GetMovieQuotesRequest extends Request {

    public static final String MOVIE_PATH = "/movie";
    public static final String QUOTE_PATH = "/quote";
    private final String movieId;

    public GetMovieQuotesRequest(String movieId) {
        super();
        this.movieId = movieId;
    }

    @Override
    public String getPath() {
        return MOVIE_PATH + "/" + movieId + QUOTE_PATH;
    }

}
