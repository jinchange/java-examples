package com.jinchanc.javaexamples.httpclient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/5 10:34
 */
public interface HttpClient {
    // custom http request error code
    int UNKNOWN_HTTP_ERROR = 5000;
    int TIMEOUT = 50001;


    Duration DEF_TIMEOUT = Duration.of(500, ChronoUnit.MILLIS);

    /**
     * send sync get request
     * @param request
     * @return
     */
    HttpResponse get(HttpRequest request);

    /**
     * send async get request
     * @param request
     * @return
     */
    HttpResponse asyncGet(HttpRequest request);

    /**
     * send sync post request
     * @param request
     * @return
     */
    HttpResponse post(HttpRequest request);

    /**
     * send async post request
     * @param request
     * @return
     */
    HttpResponse asyncPost(HttpRequest request);
}
