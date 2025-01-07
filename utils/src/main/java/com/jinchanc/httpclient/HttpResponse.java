package com.jinchanc.httpclient;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/5 10:34
 */
@Data
@Builder
public class HttpResponse {
    private @NonNull String contentType;
    private byte @NonNull [] body;
    private int status;
    private @NonNull String errorMessage;
    private long costTime;
}
