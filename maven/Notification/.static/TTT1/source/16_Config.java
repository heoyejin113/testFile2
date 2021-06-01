package com.codescroll.notification.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class Config {

  @Value("${com.codescroll.notification.web.url}")
  private String webUrl;

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    log.info("WEB URL : "+ getWebUrl());
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(30 * 1000); // 30s
    factory.setReadTimeout(30 * 1000); // 30s
    return new RestTemplate(factory);
  }

  public String getWebUrl() {
    return webUrl;
  }
}
