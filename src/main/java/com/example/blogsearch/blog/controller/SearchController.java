package com.example.blogsearch.blog.controller;

import com.example.blogsearch.blog.dto.BlogPostsDto;
import com.example.blogsearch.blog.response.BlogSearchResponse;
import com.example.blogsearch.blog.service.SearchService;
import com.example.blogsearch.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class SearchController {

    final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchBlogPosts(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "accuracy") String sort) {

        BlogPostsDto dto = searchService.searchBlogPosts(query, page, size, sort);
        return ResponseEntity.ok(ApiResponse.success(BlogSearchResponse.of(dto)));
    }

}
