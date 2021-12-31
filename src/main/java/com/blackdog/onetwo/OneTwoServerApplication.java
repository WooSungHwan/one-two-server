package com.blackdog.onetwo;

import com.blackdog.onetwo.configuration.properties.OpenAPiProperties;
import com.blackdog.onetwo.configuration.security.properties.JwtProperties;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

@EnableConfigurationProperties({JwtProperties.class, OpenAPiProperties.class})
@SpringBootApplication
public class OneTwoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneTwoServerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws NoSuchAlgorithmException, KeyManagementException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(200).setMaxConnPerRoute(200).build();

        HttpComponentsClientHttpRequestFactory customRequestFactory = new HttpComponentsClientHttpRequestFactory();
        customRequestFactory.setHttpClient(httpClient);
        customRequestFactory.setConnectTimeout(30000);  // connection timeout 30초 설정
        customRequestFactory.setReadTimeout(30000);     // read timeout 30초 설정

        return builder.requestFactory(() -> customRequestFactory).build(); //restTemplate 설정
    }
}
