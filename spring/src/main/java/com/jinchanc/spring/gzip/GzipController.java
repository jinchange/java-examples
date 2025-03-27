package com.jinchanc.spring.gzip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author 475636591@qq.com
 * @since 2025/1/21 17:13
 */
@Slf4j
@RestController
public class GzipController {

    @PostMapping("/test")
    public ResponseEntity<byte[]> test(RequestEntity<byte[]> requestEntity) {
        System.out.println(requestEntity.getHeaders().getFirst("Content-Encoding"));
        return ResponseEntity.ok(requestEntity.getBody());
    }
}
