package com.example.blogsearch.external.feign.config;

import com.example.blogsearch.external.exception.ExternalClientCanNotProceedException;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class KakaoFeignClientConfig {

    @Value("${kakao.auth.type}")
    private String KAKAO_AUTH_TYPE;

    @Value("${kakao.auth.rest-api-key}")
    private String KAKAO_AUTH_REST_API_KEY;

    private static final String EXCEPTION_MESSAGE_FORMAT = "[%s][%s] : %s";

    private static final List<HttpStatus> NORMAL_STATUS = List.of(
            HttpStatus.OK,
            HttpStatus.CREATED,
            HttpStatus.ACCEPTED,
            HttpStatus.MOVED_PERMANENTLY);

    @Bean
    public ErrorDecoder decoder() {

        return (methodKey, response) -> {
            HttpStatus responseStatus = HttpStatus.resolve(response.status());

            if (NORMAL_STATUS.contains(responseStatus)) {
                return null;
            }

            String responseBody;
            try {
                responseBody = getBodyString(response);
            } catch (IOException e) {
                throw new RuntimeException("Failed to get response body of IAM Feign client");
            }

            return new ExternalClientCanNotProceedException(
                    String.format(EXCEPTION_MESSAGE_FORMAT,
                            responseStatus,
                            methodKey,
                            responseBody), 9999);
        };

    }

    private String getBodyString(Response response) throws IOException {

        if (null == response || null == response.body()) {
            return Strings.EMPTY;
        }

        return new String(response.body().asInputStream().readAllBytes());

    }

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> requestTemplate.header(
                HttpHeaders.AUTHORIZATION, KAKAO_AUTH_TYPE + " " + KAKAO_AUTH_REST_API_KEY);

    }

}
