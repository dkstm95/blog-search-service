package com.example.blogsearch.blog.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BlogPostsDto {

    private List<BlogPostDto> blogPostDtoList;

    private Integer nextPage;

}
