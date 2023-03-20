package com.example.blogsearch.blog.service;

import com.example.blogsearch.blog.dto.BlogPostsDto;
import com.example.blogsearch.external.service.ExternalSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    final ExternalSearchService externalSearchService;

    public BlogPostsDto searchBlogPosts(String query, int page, int size, String sort) {

        return externalSearchService.searchBlogPosts(query, page, size, sort);
    }

}
