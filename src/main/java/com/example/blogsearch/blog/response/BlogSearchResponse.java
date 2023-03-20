package com.example.blogsearch.blog.response;

import com.example.blogsearch.blog.dto.BlogPostDto;
import com.example.blogsearch.blog.dto.BlogPostsDto;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class BlogSearchResponse {

    private final List<BlogPostData> list;
    private final int nextPage;

    public static BlogSearchResponse of(BlogPostsDto dto) {

        List<BlogPostData> list = dto.getBlogPostDtoList().stream()
                .map(BlogPostData::of)
                .collect(Collectors.toList());

        return BlogSearchResponse.builder()
                .list(list)
                .nextPage(dto.getNextPage())
                .build();

    }

    @Getter
    @Builder
    public static class BlogPostData {

        // 블로그 글 제목
        private String title;

        // 블로그 글 요약
        private String contents;

        // 블로그 글 URL
        private String url;

        // 카카오 한정
        // 블로그 이름
        private String blogName;

        // 카카오 한정
        // 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
        private String thumbnail;

        // 네이버 한정
        // 블로거 이름
        private String bloggerName;

        // 네이버 한정
        // 블로그 URL
        private String bloggerLink;

        // 블로그 글 작성시간
        private Instant postedAt;

        public static BlogPostData of(BlogPostDto blogPostDto) {
            return BlogPostData.builder()
                    .title(blogPostDto.getTitle())
                    .contents(blogPostDto.getContents())
                    .url(blogPostDto.getUrl())
                    .blogName(blogPostDto.getBlogName())
                    .thumbnail(blogPostDto.getThumbnail())
                    .postedAt(blogPostDto.getPostedAt())
                    .build();
        }
    }

}