package com.example.client;

import com.example.filter.BinaryOperation;
import com.example.filter.NaryOperation;
import com.example.filter.UnaryOperation;
import com.example.v1.model.Sort;
import com.example.v1.request.GetMovieRequest;
import com.example.v1.request.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RequestToURLTest {

    @BeforeAll
    public static void setUp() {
    }

    @Test
    void simplePathCheck() {
        Request request = new GetMovieRequest("abc123");

        Assertions.assertEquals("/movie/abc123", request.toUrl());
    }

    @Test
    void paginationCheck() {
        Request request = new GetMovieRequest("abc123").limit(14).page(2).offset(3).sortBy("name", Sort.Direction.ASC);

        Assertions.assertEquals("/movie/abc123?limit%3D14%26offset%3D3%26page%3D2%26sort%3Dname%3Aasc", request.toUrl());
    }

    @Test
    void filterComparisonCheck() {
        Request req = new GetMovieRequest("abc123").filter("col_abc", BinaryOperation.LessThan, "xyz");
        Assertions.assertEquals("/movie/abc123?col_abc%3Cxyz", req.toUrl());
    }
    @Test
    void filterMatchCheck() {
        Request req = new GetMovieRequest("darkWaters").filter("col_abc", BinaryOperation.Match, "xyz");

        Assertions.assertEquals("/movie/darkWaters?col_abc%3Dxyz", req.toUrl());
    }

    @Test
    void filterNegateMatchCheck() {
        Request req = new GetMovieRequest("darkWaters").filter("col_abc", BinaryOperation.NegateMatch, "xyz");
        Assertions.assertEquals("/movie/darkWaters?col_abc%21%3Dxyz", req.toUrl());
    }

    @Test
    void filterIncludeCheck() {
        Request req = new GetMovieRequest("darkWaters").filter("col_abc", NaryOperation.Include, Arrays.asList("tuv", "xyz"));
        Assertions.assertEquals("/movie/darkWaters?col_abc%3Dtuv%2Cxyz", req.toUrl());
    }

    @Test
    void filterExistCheck() {
        Request req = new GetMovieRequest("darkWaters").filter("col_abc", UnaryOperation.Exist);
        Assertions.assertEquals("/movie/darkWaters?col_abc", req.toUrl());
    }

    @Test
    void filterRegexCheck() {
        Request req = new GetMovieRequest("darkWaters").filter("col_abc", BinaryOperation.RegexEqual, "/foot/i");

        Assertions.assertEquals("/movie/darkWaters?col_abc%3D%2Ffoot%2Fi", req.toUrl());
    }
}
