package com.akm.springboot3.core.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


public class HttpClientUtils {

    private static final int REQUEST_TIMEOUT = 60000;

    private HttpClientUtils() {
        throw new IllegalStateException("HttpClientUtils Utility class");
    }

    public static <T> T get(String url, Class<T> clazz) {
        return get(url, clazz, false);
    }

    public static <T> T asyncGet(String url, Class<T> clazz) {
        return get(url, clazz, true);
    }

    public static <T> T get(String url, Class<T> clazz, boolean isAsync) {
        HttpResponse response = HttpRequest.get(url)
            .timeout(REQUEST_TIMEOUT)
            .execute(isAsync);
        return handleResponseToBean(response, clazz);
    }

    public static <T> T postForm(String url, Class<T> clazz) {
        return postForm(url, null, clazz);
    }

    public static <T> T postForm(String url, Map<String, Object> formParams, Class<T> clazz) {
        HttpResponse response = HttpRequest.post(url)
            .contentType("application/x-www-form-urlencoded")
            .form(formParams)
            .timeout(REQUEST_TIMEOUT)
            .execute();
        return handleResponseToBean(response, clazz);
    }

    public static <T> T postForm(String url, Map<String, Object> formParams, Map<String, String> headers, Class<T> clazz) {
        HttpResponse response = HttpRequest.post(url)
            .contentType("application/x-www-form-urlencoded")
            .addHeaders(headers)
            .form(formParams)
            .timeout(REQUEST_TIMEOUT)
            .execute();
        return handleResponseToBean(response, clazz);
    }

    public static <T> T postBody(String url, Object body, Map<String, String> headers, Class<T> clazz) throws JsonProcessingException {
        HttpResponse response = HttpRequest.post(url)
            .contentType("application/json")
            .addHeaders(headers)
            .timeout(REQUEST_TIMEOUT)
            .body(new ObjectMapper().writeValueAsString(body))
            .execute();
        return handleResponseToBean(response, clazz);
    }

    public static <T> T postBody(String url, String bodyJsonStr, Map<String, String> headers, Class<T> clazz) {
        HttpResponse response = HttpRequest.post(url)
            .contentType("application/json")
            .addHeaders(headers)
            .timeout(REQUEST_TIMEOUT)
            .body(bodyJsonStr)
            .execute();
        return handleResponseToBean(response, clazz);
    }

    public static URL formedUrl(String urlStr) throws MalformedURLException {
        return new URL(urlStr);
    }

    public static <T> T handleResponseToBean(HttpResponse response, Class<T> clazz) {
        return JSONUtil.toBean(response.body(), clazz);
    }
}
