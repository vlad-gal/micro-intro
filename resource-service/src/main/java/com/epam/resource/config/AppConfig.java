package com.epam.resource.config;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class AppConfig {

  @Bean
  @RequestScope
  public Tika tika() {
    return new Tika();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
