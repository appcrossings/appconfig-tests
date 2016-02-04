package com.appcrossings.configtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.appcrossings.config.HierarchicalPropertyPlaceholderConfigurer;

@ContextConfiguration(classes = {IntegrationTestApplicationContext.class})
@IntegrationTest
public class EnvironmentCascadeITCase extends AbstractTestNGSpringContextTests {

  @Autowired
  private HierarchicalPropertyPlaceholderConfigurer config;

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
