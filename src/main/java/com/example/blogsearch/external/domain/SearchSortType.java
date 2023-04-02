package com.example.blogsearch.external.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum SearchSortType {

        ACCURACY("accuracy", "sim", "정확도순"),
        RECENCY("recency", "date", "최신순"),
        ;

        private final String kakao;
        private final String naver;
        private final String viewName;

        public static SearchSortType getKakaoType(String sort) {
                return Stream.of(SearchSortType.values())
                        .filter(it -> it.getKakao().equals(sort))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("[Search] Invalid sort type"));
        }
        public String getFallback() {
                return this == ACCURACY ? ACCURACY.getNaver() : RECENCY.getNaver();
        }
}
