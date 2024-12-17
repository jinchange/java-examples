package com.jinchanc.javaexamples.httpclient;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/5 10:34
 */
@Data
@Builder
public class HttpRequest {
    // http request url
    private String url;
    // http request body
    private byte[] body;
    // http request header contentType e.g(application/json, application/octet-stream, application/x-protobuf)
    private String contentType;
    // http request header contentEncoding e.g(gzip)
    private String contentEncoding;
    // http reqeust header acceptEncoding e.g(gzip)
    private String acceptEncoding;
    // http request max waiting time, unit milliseconds
    private Duration timeout;
    // http request header userAgent e.g(Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36)
    private String userAgent;
}
