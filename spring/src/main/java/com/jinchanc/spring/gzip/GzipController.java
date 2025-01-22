package com.jinchanc.spring.gzip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjin@algorix.co
 * @since 2025/1/21 17:13
 */
@Slf4j
@RestController
public class GzipController {

    @PostMapping("/test")
    public ResponseEntity<String> test(RequestEntity<String> requestEntity) {
        log.info("request body: {}", requestEntity.getBody());
        return ResponseEntity.ok(requestEntity.getBody());
    }
}
