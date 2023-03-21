package com.example.blogsearch.blog.response;

import com.example.blogsearch.blog.dto.PopularQueryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class PopularQueriesResponse {

    private final List<PopularQueryData> list;
    private final int totalElements;

    public static PopularQueriesResponse from(List<PopularQueryDto> dto) {

        List<PopularQueryData> list = dto.stream()
                .map(it -> new PopularQueryData(it.getQuery(), it.getCount()))
                .collect(Collectors.toList());

        return PopularQueriesResponse.builder()
                .list(list)
                .totalElements(dto.size())
                .build();

    }

    @Getter
    public static class PopularQueryData {

        private final String query;
        private final int count;

        public PopularQueryData(String query, int count) {
            this.query = query;
            this.count = count;
        }

    }

}