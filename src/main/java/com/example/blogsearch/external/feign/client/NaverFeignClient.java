package com.example.blogsearch.external.feign.client;

import com.example.blogsearch.external.feign.config.NaverFeignClientConfig;
import com.example.blogsearch.external.feign.response.NaverBlogResult;
import com.example.blogsearch.external.feign.response.NaverSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "naverExternalFeign",
        url = "${external.api.url.naver}",
        configuration = NaverFeignClientConfig.class)
public interface NaverFeignClient {

    @GetMapping(value = "/v1/search/blog.json")
    NaverSearchResponse<NaverBlogResult> getBlogPosts(@RequestParam(value = "query") String query,
                                                      @RequestParam(value = "display") int page,
                                                      @RequestParam(value = "start") int size,
                                                      @RequestParam(value = "sort") String sort);
}
