package com.luka.currency.service;

import java.io.IOException;
import java.util.List;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CurrencyService {
    
    public List listQuotes() throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
            Response response = client.prepare("GET", "https://currency-exchange.p.rapidapi.com/listquotes")
                .setHeader("X-RapidAPI-Key", "c0e2c8b545msh80ba37bc0f8ec71p1d8f85jsn897bd31f95e6")
                .setHeader("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .join();

            client.close();

            String responseBody = response.getResponseBody();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseBody, List.class);
        } finally {
            client.close();
        }
    }

    public String getQuote(final String from, final String to, final String amount) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {

            Response response = client.prepare("GET", "https://currency-exchange.p.rapidapi.com/exchange?from=" + from + "&to=" + to + "&q=" + amount)
                .setHeader("X-RapidAPI-Key", "c0e2c8b545msh80ba37bc0f8ec71p1d8f85jsn897bd31f95e6")
                .setHeader("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .join();

            client.close();

            String responseBody = response.getResponseBody();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseBody, String.class);
        } finally {
            client.close();
        }
    }

}
