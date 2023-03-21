package com.example.blogsearch.blog.dto;

import lombok.Getter;

@Getter
public class PopularQueryDto {

    private final String query;
    private final int count;

    public PopularQueryDto(String query, int count) {
        this.query = query;
        this.count = count;
    }

}