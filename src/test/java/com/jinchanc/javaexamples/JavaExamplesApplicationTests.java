package com.jinchanc.javaexamples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jinchanc.javaexamples.httpclient.HttpClient;
import com.jinchanc.javaexamples.httpclient.HttpRequest;
import com.jinchanc.javaexamples.httpclient.HttpResponse;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class JavaExamplesApplicationTests {

    @Resource
    private HttpClient httpClient;

    @Test
    void testOkHttpPostRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule());
        // 不需要带accept-encoding = gzip, okhttp客户端支持自动解压缩response body
        String requestBody = "bodybodybodybodybodybodybodybodybodybodybodybodybodybodybodybody";
        HttpRequest httpRequest = HttpRequest.builder()
                .url("http://localhost:8888/gzipTest")
                .body(requestBody.getBytes(StandardCharsets.UTF_8))
                .timeout(Duration.of(1, ChronoUnit.SECONDS))
                .contentType("application/json")
                .contentEncoding(HttpRequest.ENCODING_GZIP)
                .build();
        HttpResponse httpResponse = httpClient.post(httpRequest);

        System.out.println("request data: " + objectMapper.writeValueAsString(httpRequest));
        System.out.println("response data:" + objectMapper.writeValueAsString(httpResponse));
        System.out.println("bodyString data: " + new String(httpResponse.getBody(), StandardCharsets.UTF_8));
    }

}
