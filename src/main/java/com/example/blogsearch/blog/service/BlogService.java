package com.example.blogsearch.blog.service;

import com.example.blogsearch.blog.dto.BlogPostsDto;
import com.example.blogsearch.blog.dto.PopularQueryDto;

import java.util.List;

public interface BlogService {

    BlogPostsDto searchBlogPosts(String query, int page, int size, String sort);

    List<PopularQueryDto> getPopularQueries();

}
