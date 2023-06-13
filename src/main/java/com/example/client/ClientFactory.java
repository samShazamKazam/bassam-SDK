package com.example.client;

public class ClientFactory {

    public static LotrClient createLotrClient(String base_url) {
        return new LotrClientImpl(base_url);
    }
}
