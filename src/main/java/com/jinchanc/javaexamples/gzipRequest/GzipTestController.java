package com.jinchanc.javaexamples.gzipRequest;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/18 17:50
 */
@RestController
public class GzipTestController {

    @PostMapping("/gzipTest")
    public void gzipTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contentEncoding = request.getHeader("Content-Encoding");
        String acceptEncoding = request.getHeader("Accept-Encoding");
        String contentType = request.getHeader("Content-Type");
        String requestBody;
        try (InputStream inputStream = request.getInputStream()) {
            if (Objects.equals("gzip", contentEncoding)) {
                requestBody = GzipUtil.decompressionToString(inputStream.readAllBytes());
            } else {
                requestBody = new String(inputStream.readAllBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("contentEncoding: " + contentEncoding);
        System.out.println("acceptEncoding: " + acceptEncoding);
        System.out.println("contentType: " + contentType);
        System.out.println("requestBody: " + requestBody);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            if (Objects.equals("gzip", acceptEncoding)) {
                outputStream.write(GzipUtil.compress(requestBody));
                response.setHeader("Content-Encoding", "gzip");
            } else {
                outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }
            response.setHeader("Content-Type", contentType);
        }
    }
}
