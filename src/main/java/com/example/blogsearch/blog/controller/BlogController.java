package com.example.blogsearch.blog.controller;

import com.example.blogsearch.blog.dto.BlogPostsDto;
import com.example.blogsearch.blog.dto.PopularQueryDto;
import com.example.blogsearch.blog.response.PopularQueriesResponse;
import com.example.blogsearch.blog.response.SearchBlogPostsResponse;
import com.example.blogsearch.blog.service.BlogService;
import com.example.blogsearch.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    final BlogService blogService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchBlogPosts(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "accuracy") String sort) {

        BlogPostsDto dto = blogService.searchBlogPosts(query, page, size, sort);
        return ResponseEntity.ok(ApiResponse.success(SearchBlogPostsResponse.from(dto)));

    }

    @GetMapping("/popular-queries")
    public ResponseEntity<ApiResponse> getPopularQueries() {

        List<PopularQueryDto> dto = blogService.getPopularQueries();
        return ResponseEntity.ok(ApiResponse.success(PopularQueriesResponse.from(dto)));

    }

}
