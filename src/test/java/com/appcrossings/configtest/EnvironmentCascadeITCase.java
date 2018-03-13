package com.appcrossings.configtest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.appcrossings.config.spring.ConfigrdPropertyPlaceholderConfigurer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {IntegrationTestApplicationContext.class})
public class EnvironmentCascadeITCase extends AbstractTestNGSpringContextTests {

  @Autowired
  private ConfigrdPropertyPlaceholderConfigurer config;

  @Test
  public void testFetchHostPropertiesByEnvironmentQAOnLocalClasspath() throws Exception {

    config.setHostName("does-not-exist-host");
    config.setEnvironment("QA");
    config.reload();

    String value = config.getProperty("property.1.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "custom");

    value = config.getProperty("property.2.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "value2");

  }

  @Test
  public void testFetchHostPropertiesByEnvironmentQAOnRemote() throws Exception {

    config.setHostName("does-not-exist-host");
    config.setEnvironment("DEV");
    config.reload();

    String value = config.getProperty("property.1.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "value1");

    value = config.getProperty("property.2.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "value2");

  }

}
