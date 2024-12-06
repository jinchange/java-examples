package com.jinchanc.javaexamples.httpclient;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/5 10:35
 */
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
        Assert.notNull(httpRequest, "Request must not be null");
        Assert.isTrue(StringUtils.hasText(httpRequest.getUrl()), "Url must not be empty");

        long startTime = System.currentTimeMillis();
        Request request = new Request.Builder()
                .get()
                .url(httpRequest.getUrl())
                .build();
        Duration timeout = httpRequest.getTimeout() != null ? httpRequest.getTimeout() : DEF_TIMEOUT;
        HttpResponse.HttpResponseBuilder httpResponseBuilder = HttpResponse.builder();
        try (Response response = client.newBuilder().callTimeout(timeout).build().newCall(request).execute()) {
            httpResponseBuilder.status(response.code());
            ResponseBody body;
            if (response.isSuccessful() && (body = response.body()) != null) {
                // TODO 处理json解压缩
                MediaType mediaType = body.contentType();
                httpResponseBuilder
                        .body(body.bytes())
                        .contentType(mediaType != null ? mediaType.type() : "");
            }
        } catch (IOException e) {
            if (e instanceof SocketTimeoutException) {
                httpResponseBuilder
                        .message(e.getMessage())
                        .status(TIMEOUT);
            } else {
                httpResponseBuilder
                        .message(e.getMessage())
                        .status(UNKNOWN_HTTP_ERROR);
            }
        } finally {
            httpResponseBuilder.costTime(System.currentTimeMillis() - startTime);
        }
        return httpResponseBuilder.build();
    }

    @Override
    public HttpResponse asyncGet(HttpRequest request) {
        return null;
    }

    @Override
    public HttpResponse post(HttpRequest request) {
        return null;
    }

    @Override
    public HttpResponse asyncPost(HttpRequest request) {
        return null;
    }
}
