package com.example.v1.request;

public class GetQuoteRequest extends Request {
    private final String quoteId;

    public GetQuoteRequest(String quoteId) {
        super();
        this.quoteId = quoteId;
    }

    @Override
    public String getPath() {
        return "/quote/" + quoteId;
    }
}
