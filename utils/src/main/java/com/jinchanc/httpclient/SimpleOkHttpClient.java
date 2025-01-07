package com.jinchanc.httpclient;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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

    private OkHttpClient client;

    @PostConstruct
    public void init() {
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
            log.info("OkHttpClient closed successfully");
        } catch (Exception e) {
            log.error("OkHttpClient close error", e);
        }
    }

    @Override
    public HttpResponse get(@NonNull HttpRequest httpRequest) {
        long requestTime = System.currentTimeMillis();
        Request.Builder requestBuilder = new Request.Builder()
                .get()
                .url(httpRequest.getUrl());
        Request request = requestBuilder.build();
        Duration timeout = httpRequest.getTimeout();
        HttpResponse.HttpResponseBuilder httpResponseBuilder = HttpResponse.builder();
        try (Response response = client.newBuilder().callTimeout(timeout).build().newCall(request).execute()) {
            httpResponseBuilder.status(response.code());
            ResponseBody body;
            if ((body = response.body()) != null) {
                MediaType mediaType = body.contentType();
                httpResponseBuilder
                        .body(body.bytes())
                        .contentType(mediaType != null ? mediaType.type() : "");
            }
        } catch (IOException e) {
            if (Objects.equals(e.getMessage(), "timeout")) {
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
                    .costTime(responseTime - requestTime);
        }
        return httpResponseBuilder.build();
    }

    @Override
    public HttpResponse asyncGet(HttpRequest request) {
        return null;
    }

    @Override
    public HttpResponse post(@NonNull HttpRequest httpRequest) {
        long requestTime = System.currentTimeMillis();
        Request.Builder requestBuilder = new Request.Builder()
                .url(httpRequest.getUrl())
                .addHeader("Content-Type", httpRequest.getContentType())
                .addHeader("User-Agent", httpRequest.getUserAgent());
        if (httpRequest.getContentEncoding().equalsIgnoreCase(HttpRequest.ENCODING_GZIP)) {
            requestBuilder
                    .post(RequestBody.create(GzipUtil.compress(httpRequest.getBody())))
                    .addHeader("Content-Encoding", HttpRequest.ENCODING_GZIP);
        } else {
            requestBuilder.post(RequestBody.create(httpRequest.getBody()));
        }
        Request request = requestBuilder.build();
        HttpResponse.HttpResponseBuilder httpResponseBuilder = HttpResponse.builder();
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
                    .costTime(responseTime - requestTime);
        }
        return httpResponseBuilder.build();
    }

    @Override
    public HttpResponse asyncPost(HttpRequest request) {
        return null;
    }
}
