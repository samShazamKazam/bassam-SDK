package com.example.client;

import com.example.v1.request.*;
import com.example.v1.response.HttpResponse;
import com.example.v1.response.LotrMovieResponse;
import com.example.v1.response.LotrQuoteResponse;

import java.io.IOException;

public interface LotrClient {
      HttpResponse<LotrMovieResponse> get(GetAllMoviesRequest request) throws IOException;
      HttpResponse<LotrMovieResponse> get(GetMovieRequest request) throws IOException;
      HttpResponse<LotrQuoteResponse> get(GetQuoteRequest request) throws IOException;
      HttpResponse<LotrQuoteResponse> get(GetAllQuotesRequest request) throws IOException;
      HttpResponse<LotrQuoteResponse> get(GetMovieQuotesRequest request) throws IOException;

      String getBaseUrl();
}
