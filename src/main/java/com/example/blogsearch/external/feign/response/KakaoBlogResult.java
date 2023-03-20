package com.example.blogsearch.external.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class KakaoBlogResult {

    // 블로그 글 제목
    @JsonProperty("title")
    private String title;

    // 블로그 글 요약
    @JsonProperty("contents")
    private String contents;

    // 블로그 글 URL
    @JsonProperty("url")
    private String url;

    // 블로그의 이름
    @JsonProperty("blogname")
    private String blogName;

    // 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
    @JsonProperty("thumbnail")
    private String thumbnail;

    // 블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    @JsonProperty("datetime")
    private String datetime;

}
