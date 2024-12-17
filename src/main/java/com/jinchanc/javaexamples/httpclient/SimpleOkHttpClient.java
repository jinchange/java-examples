package com.jinchanc.javaexamples.httpclient;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/5 10:35
 */
@Primary
@Slf4j
@Component
public class SimpleOkHttpClient implements HttpClient {

    private final OkHttpClient client;

    public SimpleOkHttpClient() {
        ConnectionPool connectionPool = new ConnectionPool(
                1000,
                5,
                TimeUnit.MINUTES
        );
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .followRedirects(false)
                .followSslRedirects(false)
                .connectionPool(connectionPool)
                .build();
    }

    @PreDestroy
    public void destroy() {
        try (ExecutorService executorService = client.dispatcher().executorService()) {
            executorService.shutdown();
            client.connectionPool().evictAll();
            Cache cache = client.cache();
            if (cache != null) {
                cache.close();
            }
        } catch (Exception e) {
            log.error("OkHttpClient close error", e);
        }
        log.info("OkHttpClient closed successfully");
    }


    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        long requestTime = System.currentTimeMillis();
        Assert.isTrue(httpRequest != null && StringUtils.hasText(httpRequest.getUrl()), "Invalid httpRequest");
        Request.Builder requestBuilder = new Request.Builder()
                .get()
                .url(httpRequest.getUrl());
        if (httpRequest.getUserAgent() != null) {
            requestBuilder.addHeader("User-Agent", httpRequest.getUserAgent());
        }
        if (httpRequest.getAcceptEncoding() != null) {
            requestBuilder.addHeader("Accept-Encoding", httpRequest.getAcceptEncoding());
        }
        Request request = requestBuilder.build();
        Duration timeout = httpRequest.getTimeout() != null ? httpRequest.getTimeout() : DEF_TIMEOUT;
        HttpResponse.HttpResponseBuilder httpResponseBuilder = HttpResponse.builder();
        try (Response response = client.newBuilder().callTimeout(timeout).build().newCall(request).execute()) {
            httpResponseBuilder.status(response.code());
            ResponseBody body;
            if (response.isSuccessful() && (body = response.body()) != null) {
                MediaType mediaType = body.contentType();
                httpResponseBuilder
                        .body(body.bytes())
                        .contentType(mediaType != null ? mediaType.type() : "");
            }
        } catch (IOException e) {
            if (e instanceof SocketTimeoutException) {
                httpResponseBuilder
                        .errorMessage(e.getMessage())
                        .status(TIMEOUT);
            } else {
                httpResponseBuilder
                        .errorMessage(e.getMessage())
                        .status(UNKNOWN_HTTP_ERROR);
            }
        } finally {
            long responseTime = System.currentTimeMillis();
            httpResponseBuilder
                    .requestTime(requestTime)
                    .responseTime(responseTime)
                    .costTime(responseTime - requestTime);
        }
        return httpResponseBuilder.build();
    }

    @Override
    public HttpResponse asyncGet(HttpRequest request) {
        return null;
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        long requestTime = System.currentTimeMillis();
        Request.Builder requestBuilder = new Request.Builder()
                .post(RequestBody.create(httpRequest.getBody(), MediaType.parse(httpRequest.getContentType())))
                .url(httpRequest.getUrl())
                .addHeader("Content-Type", httpRequest.getContentType())
                .addHeader("Content-Encoding", httpRequest.getContentEncoding())
                .addHeader("Accept-Encoding", httpRequest.getContentEncoding())
                .addHeader("User-Agent", httpRequest.getUserAgent());
        HttpResponse.HttpResponseBuilder httpResponseBuilder = HttpResponse.builder();
        Request request = requestBuilder.build();
        try (Response response = client.newBuilder().callTimeout(httpRequest.getTimeout()).build().newCall(request).execute()) {
            httpResponseBuilder.status(response.code());
            ResponseBody body;
            if ((body = response.body()) != null) {
                MediaType mediaType = body.contentType();
                httpResponseBuilder
                        .body(body.bytes())
                        .contentType(mediaType != null ? mediaType.toString() : "");
            }
        } catch (IOException e) {
            if (e instanceof SocketTimeoutException) {
                httpResponseBuilder
                        .errorMessage(e.getMessage())
                        .status(TIMEOUT);
            } else {
                httpResponseBuilder
                        .errorMessage(e.getMessage())
                        .status(UNKNOWN_HTTP_ERROR);
            }
        } finally {
            long responseTime = System.currentTimeMillis();
            httpResponseBuilder
                    .requestTime(requestTime)
                    .responseTime(responseTime)
                    .costTime(responseTime - requestTime);
        }
        return httpResponseBuilder.build();
    }

    @Override
    public HttpResponse asyncPost(HttpRequest request) {
        return null;
    }
}
