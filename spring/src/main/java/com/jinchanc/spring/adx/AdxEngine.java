package com.jinchanc.spring.adx;

import java.util.List;

/**
 * @author zhangjin@algorix.co
 * @since 2025/1/24 17:01
 */
public interface AdxEngine {

    MidRequest acceptSellerReqeust(int sellerId, byte[] requestBodyBytes);

    MidResponse sendAuctionRequest(MidRequest midRequest, List<Buyer> buyerList);
}

record MidRequest() {}

record MidResponse() {}

record Buyer() {}
