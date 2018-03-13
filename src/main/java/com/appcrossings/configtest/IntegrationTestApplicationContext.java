package com.appcrossings.configtest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import com.appcrossings.config.spring.ConfigrdPropertyPlaceholderConfigurer;

@SpringBootApplication(scanBasePackages = {"com.appcrossings.configtest"})
@PropertySource("classpath:/application.properties")
public class IntegrationTestApplicationContext {

  @Bean
  public SampleClass sample() {
    return new SampleClass();
  }

  @Bean
  public static ConfigrdPropertyPlaceholderConfigurer buildConfig() throws Exception {
    ConfigrdPropertyPlaceholderConfigurer config =
        new ConfigrdPropertyPlaceholderConfigurer(
            "http://localhost:8889/repoName/env/hosts.properties");
    config.setPassword("secret");
    return config;
  }

}
