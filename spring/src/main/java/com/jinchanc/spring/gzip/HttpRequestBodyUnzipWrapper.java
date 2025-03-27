package com.jinchanc.spring.gzip;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author 475636591@qq.com
 * @since 2025/1/21 14:51
 */
public class HttpRequestBodyUnzipWrapper extends HttpServletRequestWrapper {

    private final byte[] cacheBytes;

    public HttpRequestBodyUnzipWrapper(HttpServletRequest request) {
        super(request);
        try (ServletInputStream inputStream = request.getInputStream()) {
            if (Objects.equals(request.getHeader("Content-Encoding"), "gzip")) {
                cacheBytes = GzipUtil.decompression(StreamUtils.copyToByteArray(inputStream));
            } else {
                cacheBytes = StreamUtils.copyToByteArray(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cacheBytes);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
