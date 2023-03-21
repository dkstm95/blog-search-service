package com.example.blogsearch.blog.service.impl;

import com.example.blogsearch.blog.dto.BlogPostsDto;
import com.example.blogsearch.blog.service.BlogQueryService;
import com.example.blogsearch.blog.service.BlogSearchService;
import com.example.blogsearch.external.service.ExternalSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogSearchServiceImpl implements BlogSearchService {

    final BlogQueryService blogQueryService;
    final ExternalSearchService externalSearchService;

    @Override
    public BlogPostsDto searchBlogPosts(String query, int page, int size, String sort) {

        blogQueryService.incrementQueryCount(query);
        return externalSearchService.searchBlogPosts(query, page, size, sort);

    }

}
