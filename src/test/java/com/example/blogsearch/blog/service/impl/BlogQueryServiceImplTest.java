package com.example.blogsearch.blog.service.impl;

import com.example.blogsearch.blog.dto.PopularQueryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BlogQueryServiceImplTest {

    @Mock
    private RedissonClient redissonClient;

    @Mock
    private RScoredSortedSet<Object> popularQueries;

    @InjectMocks
    private BlogQueryServiceImpl blogQueryService;

    @Test
    void 인기검색어와_카운트가_redis에_존재할때() {

        // given
        given(redissonClient.getScoredSortedSet("popular_queries")).willReturn(popularQueries);

        List<Object> queryList = List.of("query1", "query2", "query3", "query4", "query5");
        given(popularQueries.valueRangeReversed(0, 9)).willReturn(queryList);
        given(popularQueries.getScore("query1")).willReturn(10.0);
        given(popularQueries.getScore("query2")).willReturn(9.0);
        given(popularQueries.getScore("query3")).willReturn(8.0);
        given(popularQueries.getScore("query4")).willReturn(7.0);
        given(popularQueries.getScore("query5")).willReturn(6.0);

        // when
        List<PopularQueryDto> popularQueryList = blogQueryService.getPopularQueries();

        // then
        assertAll("popularQueryList",
                () -> assertEquals(5, popularQueryList.size()),
                () -> assertEquals("query1", popularQueryList.get(0).getQuery()),
                () -> assertEquals(10, popularQueryList.get(0).getCount()),
                () -> assertEquals("query2", popularQueryList.get(1).getQuery()),
                () -> assertEquals(9, popularQueryList.get(1).getCount()),
                () -> assertEquals("query3", popularQueryList.get(2).getQuery()),
                () -> assertEquals(8, popularQueryList.get(2).getCount()),
                () -> assertEquals("query4", popularQueryList.get(3).getQuery()),
                () -> assertEquals(7, popularQueryList.get(3).getCount()),
                () -> assertEquals("query5", popularQueryList.get(4).getQuery()),
                () -> assertEquals(6, popularQueryList.get(4).getCount())
        );

    }

    @Test
    void incrementQueryCount가_호출되면_redis의_검색어_카운트가_1_올라야한다() {

        // given
        given(redissonClient.getScoredSortedSet("popular_queries")).willReturn(popularQueries);

        // when
        blogQueryService.incrementQueryCount("query");

        // then
        verify(popularQueries, times(1)).addScore("query", 1);

    }

}