package com.jinchanc.javaexamples.httpclient;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/5 10:34
 */
@Data
@Builder
public class HttpRequest {
    public static final String ENCODING_GZIP = "gzip";
    // http request url
    private @NonNull String url;
    // http request body
    private byte @NonNull [] body;
    // http request header contentType e.g(application/json, application/octet-stream, application/x-protobuf)
    private @NonNull String contentType;
    // http request header contentEncoding e.g(gzip)
    private @NonNull String contentEncoding;
    // http reqeust header acceptEncoding e.g(gzip)
    private @NonNull Duration timeout;
}
