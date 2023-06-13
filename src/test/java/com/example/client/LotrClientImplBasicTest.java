package com.example.client;


import com.example.v1.model.Movie;
import com.example.v1.response.LotrMovieResponse;
import com.example.v1.model.Quote;
import com.example.v1.response.HttpResponse;
import com.example.v1.response.LotrQuoteResponse;
import com.example.v1.request.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LotrClientImplBasicTest {

    private static WireMockServer wireMockServer;
    private static LotrClient client;
    private static String base_url;

    @BeforeAll
    public static void setUp() throws IOException {
        // Start the WireMock server on a specific port
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        String data = readResourceFile("all_movies.json");
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/movie"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(data)));

        data = readResourceFile("single_movie.json");
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/movie/5cd95395de30eff6ebccde5b"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(data)));

        data = readResourceFile("single_movie_quote_page_1.json");
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/movie/5cd95395de30eff6ebccde5b/quote"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(data)));

        data = readResourceFile("all_quotes.json");
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/quote"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(data)));

        data = readResourceFile("one_quote.json");
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/quote/5cd96e05de30eff6ebccebd0"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(data)));

        base_url = "http://localhost:8080";
        client = new LotrClientImpl(base_url);
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void listMovies() throws IOException {
        HttpResponse<LotrMovieResponse> response = client.get(new GetAllMoviesRequest());
        List<Movie> movies = response.getBody().get().getDocs();
        Assertions.assertEquals(8, movies.size());
    }

    @Test
    void getOneMovie() throws IOException {
        GetMovieRequest getOneMovie = new GetMovieRequest("5cd95395de30eff6ebccde5b");
        HttpResponse<LotrMovieResponse> response = client.get(getOneMovie);
        List<Movie> movies = response.getBody().get().getDocs();
        Assertions.assertEquals(1, movies.size());
    }

    @Test
    void getQuotesForMovie() throws IOException {
        GetMovieQuotesRequest getOneMovie = new GetMovieQuotesRequest("5cd95395de30eff6ebccde5b");
        HttpResponse<LotrQuoteResponse> response = client.get(getOneMovie);
        List<Quote> quotes = response.getBody().get().getDocs();
        Assertions.assertEquals(1000, quotes.size());
    }

    @Test
    void getAllQuotes() throws IOException {
        GetAllQuotesRequest getAllQuotes = new GetAllQuotesRequest();
        HttpResponse<LotrQuoteResponse> response = client.get(getAllQuotes);
        List<Quote> quotes = response.getBody().get().getDocs();
        Assertions.assertEquals(1000, quotes.size());
    }

    @Test
    void getOneQuote() throws IOException {
        GetQuoteRequest getQuote = new GetQuoteRequest("5cd96e05de30eff6ebccebd0");
        HttpResponse<LotrQuoteResponse> response = client.get(getQuote);
        List<Quote> quotes = response.getBody().get().getDocs();
        Assertions.assertEquals(1, quotes.size());
    }

    private static String readResourceFile(String resourceName) throws IOException {
        ClassLoader classLoader = LotrClientImplBasicTest.class.getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();

        Path path = Paths.get(absolutePath);
        return Files.readString(path);
    }
}