package com.jinchanc.spring.gzip;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author zhangjin@algorix.co
 * @since 2025/1/21 14:54
 */
public class HttpResponseBodyZipWrapper extends HttpServletResponseWrapper {

    // 缓存起写入的字节流
    private final ByteArrayOutputStream cacheByteArray = new ByteArrayOutputStream();

    public HttpResponseBodyZipWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
            @Override
            public void write(int b) {
                cacheByteArray.write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener listener) {

            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(cacheByteArray);
    }

    // 使用缓存的字节流写入到输出流
    public void rewrite(String encoding) {
        // TODO 这个卡死bug难以解决
        try (ServletOutputStream outputStream = this.getResponse().getOutputStream()) {
            byte[] byteArray = cacheByteArray.toByteArray();
            if (Objects.equals(encoding, "gzip")) {
                byte[] compressBytes = GzipUtil.compress(byteArray);
                outputStream.write(compressBytes);
                this.getResponse().setContentLength(compressBytes.length);
                this.getResponse().setContentLength(compressBytes.length);
                setHeader("Content-Encoding", "gzip");
            } else {
                outputStream.write(byteArray);
                this.getResponse().setContentLength(byteArray.length);
                this.getResponse().setContentLength(byteArray.length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
