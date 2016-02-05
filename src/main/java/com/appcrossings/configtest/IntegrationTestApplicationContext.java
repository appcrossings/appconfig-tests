package com.appcrossings.configtest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import com.appcrossings.config.HierarchicalPropertyPlaceholderConfigurer;

@SpringBootApplication(scanBasePackages = {"com.appcrossings.configtest"})
@PropertySource("classpath:/application.properties")
public class IntegrationTestApplicationContext {

  @Bean
  public SampleClass sample() {
    return new SampleClass();
  }

  @Bean
  public static HierarchicalPropertyPlaceholderConfigurer buildConfig() throws Exception {
    HierarchicalPropertyPlaceholderConfigurer config =
        new HierarchicalPropertyPlaceholderConfigurer(
            "http://localhost:8889/config/env/hosts.properties");
    config.setPassword("secret");
    return config;
  }

}
