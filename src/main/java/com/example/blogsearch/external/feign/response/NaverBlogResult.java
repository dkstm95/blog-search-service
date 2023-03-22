package com.example.blogsearch.external.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class NaverBlogResult {

    // 블로그 글 제목
    @JsonProperty("title")
    private String title;

    // 블로그 글 요약
    @JsonProperty("description")
    private String description;

    // 블로그 글 URL
    @JsonProperty("link")
    private String link;

    // 블로거 이름
    @JsonProperty("bloggername")
    private String bloggerName;

    // 블로그 링크
    @JsonProperty("bloggerlink")
    private String bloggerLink;

    // 블로그 글 작성일시, YYYYMMDD
    @JsonProperty("postdate")
    private String postdate;

}
