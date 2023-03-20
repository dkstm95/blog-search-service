package com.example.blogsearch.external.service;

import com.example.blogsearch.blog.dto.BlogPostsDto;

public interface ExternalSearchService {

    BlogPostsDto searchBlogPosts(String query, int page, int size, String sort);

}
