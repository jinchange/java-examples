package com.jinchanc.javaexamples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jinchanc.javaexamples.gzipRequest.GzipUtil;
import com.jinchanc.javaexamples.httpclient.HttpClient;
import com.jinchanc.javaexamples.httpclient.HttpRequest;
import com.jinchanc.javaexamples.httpclient.HttpResponse;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@SpringBootTest
class JavaExamplesApplicationTests {

    @Resource
    private HttpClient httpClient;

    @Test
    void contextLoads() throws IOException {
        String body = """
                {
                	"id": "449898e5111c4bcd98ca54306c27be3b",
                	"imps": [{
                		"id": "testAdSlotId",
                		"adSlotType": 3,
                		"bidFloor": 50,
                		"interactionTypes": [1, 2, 3],
                		"image": {
                			"w": 1080,
                			"h": 1920,
                			"count": 1
                		}
                	}],
                	"app": {
                		"id": "testAppId",
                		"name": "testAppName",
                		"bundle": "testAppBundle",
                		"ver": "6.33.0"
                	},
                	"device": {
                		"ua": "Mozilla/5.0 (Linux; Android 7.1.1; vivo Y75A Build/N6F26Q; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36",
                		"geo": {
                			"lat": 40.7127,
                			"lon": 74.0059,
                			"city": "GuangZhou"
                		},
                		"ipv4": "39.149.229.215",
                		"deviceType": 1,
                		"brand": "vivo",
                		"model": "s19",
                		"os": 2,
                		"osv": "14.0",
                		"screenWidth": 1080,
                		"screenHeight": 1920,
                		"screenOrientation": 1,
                		"connectionType": 7,
                		"ppi": 6,
                		"density": 1.2000000476837158,
                		"bootMark": "BootMark",
                		"updateMark": "UpdateMark",
                		"bootTime": "BootTime",
                		"updateTime": "UpdateTime",
                		"mac": "macmacmacmac",
                		"oaid": "oaidoaidoaidoaid",
                		"make": "vivo"
                	},
                	"user": {
                		"gender": 1,
                		"age": 18,
                		"installedAppBundles": ["baidu.com", "jd.com"]
                	},
                	"secure": true,
                	"apiVersion": "1.0.0",
                	"tmax": 500,
                    "test": 1
                }
                """;
        HttpRequest httpRequest = HttpRequest.builder()
                .url("http://localhost:8080/rtb/52123")
                .body(body.getBytes(StandardCharsets.UTF_8))
                .timeout(Duration.of(1, ChronoUnit.SECONDS))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .contentEncoding("gzip")
                .acceptEncoding("gzip")
                .userAgent("ok httpclient")
                .build();
        HttpResponse httpResponse = httpClient.post(httpRequest);
        ObjectMapper objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule());
        System.out.println(objectMapper.writeValueAsString(httpRequest));
        System.out.println(objectMapper.writeValueAsString(httpResponse));
        System.out.println("balba: " +new String(httpResponse.getBody(), StandardCharsets.UTF_8));
        // TODO 测试自带的http gzip压缩和解压缩
    }

}
