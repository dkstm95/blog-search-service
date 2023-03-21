package com.example.blogsearch.external.service;

import com.example.blogsearch.blog.domain.SearchSortType;
import com.example.blogsearch.blog.dto.BlogPostDto;
import com.example.blogsearch.blog.dto.BlogPostsDto;
import com.example.blogsearch.external.exception.ExternalClientCanNotProceedException;
import com.example.blogsearch.external.feign.client.KakaoFeignClient;
import com.example.blogsearch.external.feign.client.NaverFeignClient;
import com.example.blogsearch.external.feign.response.KakaoBlogResult;
import com.example.blogsearch.external.feign.response.KakaoSearchResponse;
import com.example.blogsearch.external.feign.response.NaverBlogResult;
import com.example.blogsearch.external.feign.response.NaverSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalSearchServiceImpl implements ExternalSearchService {

    private static final DateTimeFormatter KAKAO_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static final DateTimeFormatter NAVER_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    final KakaoFeignClient kakaoFeignClient;
    final NaverFeignClient naverFeignClient;

    @Override
    public BlogPostsDto searchBlogPosts(String query, int page, int size, String sort) {

        SearchSortType sortType = SearchSortType.getKakaoType(sort);

        try {
            KakaoSearchResponse<KakaoBlogResult> kakaoBlogPostResponse = kakaoFeignClient.getBlogPosts(query, page, size,
                    sortType.getKakao());
            log.info("[External][Feign] Kakao API call success. (query: {}, page: {}, size: {}, sort: {})", query, page, size, sort);
            return convertKakaoBlogPostsToDto(kakaoBlogPostResponse, page);
        } catch (ExternalClientCanNotProceedException e) {
            log.error("[External][Feign] Kakao API call failed. (query: {}, page: {}, size: {}, sort: {})", query, page, size, sort);
            try {
                NaverSearchResponse<NaverBlogResult> naverBlogPostList = naverFeignClient.getBlogPosts(query, size, page,
                        sortType.getFallback());
                log.info("[External][Feign] Naver API call success. (query: {}, page: {}, size: {}, sort: {})", query, page, size, sort);
                return convertNaverBlogPostsToDto(naverBlogPostList);
            } catch (ExternalClientCanNotProceedException ex) {
                throw new RuntimeException("External API call failed. (query: " + query + ", page: " + page + ", size: " + size + ", sort: " + sort + ")", ex);
            }
        }

    }

    private BlogPostsDto convertKakaoBlogPostsToDto(KakaoSearchResponse<KakaoBlogResult> kakaoBlogPostList, int page) {

        List<BlogPostDto> blogPostDtoList = kakaoBlogPostList.getDocuments().stream()
                .map(it -> BlogPostDto.builder()
                        .title(it.getTitle())
                        .contents(it.getContents())
                        .url(it.getUrl())
                        .blogName(it.getBlogName())
                        .thumbnail(it.getThumbnail())
                        .postedAt(Instant.from(KAKAO_DATETIME_FORMATTER.parse(it.getDatetime())))
                        .build())
                .collect(Collectors.toList());

        Integer nextPage = kakaoBlogPostList.getMeta().isEnd() ? null : page + 1;
        int totalElements = kakaoBlogPostList.getMeta().getPageableCount();

        return BlogPostsDto.builder()
                .blogPostDtoList(blogPostDtoList)
                .nextPage(nextPage)
                .totalElements(totalElements)
                .build();

    }

    private BlogPostsDto convertNaverBlogPostsToDto(NaverSearchResponse<NaverBlogResult> naverBlogPostList) {

        List<BlogPostDto> blogPostDtoList = naverBlogPostList.getItems().stream()
                .map(it -> {
                    LocalDate date = LocalDate.parse(it.getDatetime(), NAVER_DATETIME_FORMATTER);
                    Instant instant = date.atStartOfDay().toInstant(ZoneOffset.UTC);
                    return BlogPostDto.builder()
                        .title(it.getTitle())
                        .contents(it.getDescription())
                        .url(it.getLink())
                        .bloggerName(it.getBloggerName())
                        .bloggerLink(it.getBloggerLink())
                        .postedAt(instant)
                        .build();
                })
                .collect(Collectors.toList());

        int currentPageNumber = naverBlogPostList.getStart();
        int size = naverBlogPostList.getDisplay();
        int total = naverBlogPostList.getTotal();
        Integer nextPage = null;
        if (currentPageNumber * size + size < total) {
            nextPage = currentPageNumber + 1;
        }

        return BlogPostsDto.builder()
                .blogPostDtoList(blogPostDtoList)
                .nextPage(nextPage)
                .totalElements(total)
                .build();

    }

}
