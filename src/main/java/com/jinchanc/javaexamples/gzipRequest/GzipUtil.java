package com.jinchanc.javaexamples.gzipRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author zhangjin@algorix.co
 * @since 2023/10/13 10:53
 */
@Slf4j
public class GzipUtil {

    /**
     * 压缩
     * string -> gzip byteArray
     */
    public static byte[] compress(String str) {
        if (!StringUtils.hasText(str))
            throw new RuntimeException("gzip String is empty.");
        return compress(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解压缩
     * gzip byteArray -> string
     */
    public static String decompressionToString(byte[] byteArray) throws IOException {
        byteArray = decompression(byteArray);
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    /**
     * 压缩
     * byteArray -> gzip byteArray
     */
    public static byte[] compress(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0) {
            throw new RuntimeException("gzip byteArray is empty.");
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(byteArray);
            gzip.finish();
            byteArray = out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArray;
    }

    /**
     * 解压缩
     * gzip byteArray -> byteArray
     */
    public static byte[] decompression(byte[] byteArray) throws IOException {
        if (byteArray == null || byteArray.length == 0) {
            throw new RuntimeException("unzip byteArray is empty.");
        }
        try (ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(byteArray);
             GZIPInputStream gzipInput = new GZIPInputStream(byteArrayInput);
             ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gzipInput.read(buffer)) != -1) {
                byteArrayOutput.write(buffer, 0, n);
            }
            return byteArrayOutput.toByteArray();
        }
    }

}
