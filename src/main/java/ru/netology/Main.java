package ru.netology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public record Facts(String id, String text, String type, String user, String upvotes) {
    }

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = httpClientCreate();
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Facts> factsList = objectMapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {
                }
        );
        List<Facts> sortedFacts = new ArrayList<>();
        factsList.stream().filter(value -> value.upvotes != null && Integer.parseInt(value.upvotes) > 0);

        System.out.println(factsList);
    }


    public static CloseableHttpClient httpClientCreate() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        return httpClient;
    }
}
