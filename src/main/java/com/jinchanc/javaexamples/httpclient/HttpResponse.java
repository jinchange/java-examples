package com.jinchanc.javaexamples.httpclient;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/5 10:34
 */
@Data
@Builder
public class HttpResponse {
    // http response header contentType
    private String contentType;
    // http response body
    private byte[] body;
    // http response status e.g(200,400,500)
    private int status;
    // http response error message
    private String errorMessage;
    // http request timestamp
    private long requestTime;
    // http response timestamp
    private long responseTime;
    // http reqeust -> response cost time
    private long costTime;
}
