package com.example.blogsearch.external.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class KakaoSearchResponse<T> {

    private List<T> documents;
    private SearchMeta meta;

    @Getter
    public static class SearchMeta {
        @JsonProperty("total_count")
        private int totalCount;

        @JsonProperty("pageable_count")
        private int pageableCount;

        @JsonProperty("is_end")
        private boolean isEnd;
    }

}
