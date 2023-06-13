package com.example.v1.request;

public class GetAllQuotesRequest extends Request {

    public GetAllQuotesRequest() {
        super();
    }

    @Override
    public String getPath() {
        return "/quote";
    }

}
