package com.example.blogsearch.blog.service;

import com.example.blogsearch.blog.dto.BlogPostsDto;

public interface BlogSearchService {

    BlogPostsDto searchBlogPosts(String query, int page, int size, String sort);

}
