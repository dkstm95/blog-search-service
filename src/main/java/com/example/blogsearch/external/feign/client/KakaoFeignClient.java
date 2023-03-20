package com.example.blogsearch.external.feign.client;

import com.example.blogsearch.external.feign.config.KakaoFeignClientConfig;
import com.example.blogsearch.external.feign.response.KakaoBlogResult;
import com.example.blogsearch.external.feign.response.KakaoSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakaoExternalFeign",
        url = "${external.api.url.kakao}",
        configuration = KakaoFeignClientConfig.class)
public interface KakaoFeignClient {

    @GetMapping(value = "/v2/search/blog")
    KakaoSearchResponse<KakaoBlogResult> getBlogPosts(@RequestParam(value = "query") String query,
                                                     @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size,
                                                      @RequestParam(value = "sort") String sort);
}
