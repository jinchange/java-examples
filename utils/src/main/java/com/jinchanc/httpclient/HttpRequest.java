package com.jinchanc.httpclient;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;

/**
 * @author 475636591@qq.com
 * @since 2024/12/5 10:34
 */
@Data
@Builder
public class HttpRequest {
    public static final String ENCODING_GZIP = "gzip";

    private @NonNull String url;
    private byte @NonNull [] body;
    private @NonNull String contentType;
    private @NonNull String contentEncoding;
    private @NonNull Duration timeout;
    private @NonNull String userAgent;
}
