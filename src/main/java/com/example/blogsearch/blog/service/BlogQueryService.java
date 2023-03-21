package com.example.blogsearch.blog.service;

import com.example.blogsearch.blog.dto.PopularQueryDto;

import java.util.List;

public interface BlogQueryService {

    List<PopularQueryDto> getPopularQueries();

    void incrementQueryCount(String query);

}