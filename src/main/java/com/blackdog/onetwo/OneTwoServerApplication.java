package com.blackdog.onetwo;

import com.blackdog.onetwo.configuration.properties.AmazonProperties;
import com.blackdog.onetwo.configuration.properties.AmazonS3Properties;
import com.blackdog.onetwo.configuration.properties.OpenAPiProperties;
import com.blackdog.onetwo.configuration.security.properties.JwtProperties;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

@EnableJpaAuditing
@EnableConfigurationProperties({JwtProperties.class, OpenAPiProperties.class, AmazonProperties.class, AmazonS3Properties.class})
@SpringBootApplication
public class OneTwoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneTwoServerApplication.class, args);
    }

}
