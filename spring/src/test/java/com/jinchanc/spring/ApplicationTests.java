package com.jinchanc.spring;

import com.jinchanc.spring.gzip.GzipUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() throws IOException {
        String str = "{\"id\":\"449898e5111c4bcd98ca54306c27be3b\",\"imps\":[{\"id\":\"testAdSlotId\",\"adSlotType\":5,\"bidFloor\":50,\"interactionTypes\":[1,2,3],\"video\":{\"w\":1080,\"h\":1920}}],\"app\":{\"id\":\"testAppId\",\"name\":\"testAppName\",\"bundle\":\"testAppBundle\",\"ver\":\"6.33.0\"},\"device\":{\"ua\":\"Mozilla/5.0 (Linux; Android 7.1.1; vivo Y75A Build/N6F26Q; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36\",\"geo\":{\"lat\":40.7127,\"lon\":74.0059,\"city\":\"GuangZhou\"},\"ipv4\":\"39.149.229.215\",\"deviceType\":1,\"brand\":\"vivo\",\"model\":\"s19\",\"os\":2,\"osv\":\"14.0\",\"screenWidth\":1080,\"screenHeight\":1920,\"screenOrientation\":1,\"connectionType\":7,\"ppi\":6,\"density\":1.2000000476837158,\"bootMark\":\"BootMark\",\"updateMark\":\"UpdateMark\",\"bootTime\":\"BootTime\",\"updateTime\":\"UpdateTime\",\"mac\":\"macmacmacmac\",\"oaid\":\"oaidoaidoaidoaid\",\"make\":\"vivo\",\"idfv\":\"idfv\"},\"user\":{\"gender\":1,\"age\":18,\"installedAppBundles\":[\"baidu.com\",\"jd.com\"]},\"secure\":true,\"apiVersion\":\"1.0.0\",\"tmax\":500,\"test\":1}";
        GzipUtil.saveToBinaryFile(GzipUtil.compress(str));
    }

}
