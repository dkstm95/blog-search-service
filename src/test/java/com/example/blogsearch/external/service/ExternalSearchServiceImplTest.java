package com.example.blogsearch.external.service;

import com.example.blogsearch.blog.dto.BlogPostsDto;
import com.example.blogsearch.blog.service.BlogQueryService;
import com.example.blogsearch.blog.service.impl.BlogSearchServiceImpl;
import com.example.blogsearch.external.exception.ExternalSearchServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExternalSearchServiceImplTest {

    @Mock
    private BlogQueryService blogQueryService;

    @Mock
    private ExternalSearchService externalSearchService;

    @InjectMocks
    private BlogSearchServiceImpl blogSearchService;

    @Test
    public void 정상적인_블로그_검색_테스트() {

        // given
        String query = "test";
        int page = 0;
        int size = 10;
        String sort = "accuracy";
        BlogPostsDto expectedBlogPostsDto = BlogPostsDto.builder()
                .blogPostDtoList(Collections.emptyList())
                .nextPage(null)
                .totalElements(0)
                .build();

        given(externalSearchService.searchBlogPosts(query, page, size, sort)).willReturn(expectedBlogPostsDto);

        // when
        BlogPostsDto actualBlogPostsDto = blogSearchService.searchBlogPosts(query, page, size, sort);

        // then
        assertEquals(expectedBlogPostsDto, actualBlogPostsDto);
        verify(blogQueryService, times(1)).incrementQueryCount(query);

    }

    @Test
    public void 외부_검색서비스_API_호출_결과가_null이면_Exception을_발생시키고_검색어_카운트가_증가되지_않아야한다() {

        // given
        String query = "test";
        int page = 0;
        int size = 10;
        String sort = "accuracy";
        given(externalSearchService.searchBlogPosts(query, page, size, sort)).willReturn(null);

        // when, then
        assertThrows(ExternalSearchServiceException.class, () -> blogSearchService.searchBlogPosts(query, page, size, sort));
        verify(blogQueryService, times(0)).incrementQueryCount(query);

    }

}