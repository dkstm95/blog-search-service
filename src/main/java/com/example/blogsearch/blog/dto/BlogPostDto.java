package com.example.blogsearch.blog.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class BlogPostDto {

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
    // 카카오 -> ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    // 네이버 -> YYYYMMDD
    private Instant postedAt;

}
