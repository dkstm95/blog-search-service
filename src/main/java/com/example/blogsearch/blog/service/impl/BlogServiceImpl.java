package com.example.blogsearch.blog.service.impl;

import com.example.blogsearch.blog.dto.BlogPostsDto;
import com.example.blogsearch.blog.dto.PopularQueryDto;
import com.example.blogsearch.blog.service.BlogService;
import com.example.blogsearch.external.service.ExternalSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogServiceImpl implements BlogService {

    private static final String POPULAR_QUERIES_KEY = "popular_queries";

    final ExternalSearchService externalSearchService;

    final RedissonClient redissonClient;

    @Override
    public BlogPostsDto searchBlogPosts(String query, int page, int size, String sort) {

        incrementQueryCount(query);
        return externalSearchService.searchBlogPosts(query, page, size, sort);

    }

    @Override
    public List<PopularQueryDto> getPopularQueries() {

        RScoredSortedSet<String> popularQueries = redissonClient.getScoredSortedSet(POPULAR_QUERIES_KEY);
        Collection<String> top10PopularQueries = popularQueries.valueRangeReversed(0, 9);
        return top10PopularQueries.stream()
                .map(query -> new PopularQueryDto(query, popularQueries.getScore(query).intValue()))
                .collect(Collectors.toList());

    }

    private void incrementQueryCount(String query) {

        RScoredSortedSet<String> popularQueries = redissonClient.getScoredSortedSet(POPULAR_QUERIES_KEY);
        popularQueries.addScore(query, 1);

        log.info("[Redis][Query] increment query count: {}", query);

    }

}
