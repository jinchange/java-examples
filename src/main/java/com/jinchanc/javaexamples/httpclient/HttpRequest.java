package com.jinchanc.javaexamples.httpclient;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
}
