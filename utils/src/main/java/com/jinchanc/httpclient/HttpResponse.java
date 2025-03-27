package com.jinchanc.httpclient;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @author 475636591@qq.com
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
