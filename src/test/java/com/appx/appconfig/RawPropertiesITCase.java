package com.appx.appconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appx.HierarchicalPropertyPlaceholderConfigurer;


@SpringApplicationConfiguration(classes = {AppConfigServiceApplicationContext.class})
@WebIntegrationTest
public class RawPropertiesITCase extends AbstractTestNGSpringContextTests {

  @Value("${server.port}")
  private String port;

  private HierarchicalPropertyPlaceholderConfigurer config;

  @BeforeClass
  public void env() {
    System.setProperty("env", "QA");
  }

  @AfterClass
  public void destroyEnv() {
    System.setProperty("env", "");
  }

  @BeforeMethod
  public void init() throws Exception {

    config =
        new HierarchicalPropertyPlaceholderConfigurer("http://localhost:" + port
            + "/appconfig/env/hosts.properties");
    config.setPassword("secret");
    
  }

  @Test
  public void testFetchHostPropertiesByHostName() throws Exception {
    
    config.setHostName("remote-host"); //host setting will override env setting
    config.init();

    String value = config.getProperty("property.1.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "value1");

    value = config.getProperty("property.2.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "value2");

  }
  
  @Test
  public void testFetchHostPropertiesByEnvironmentQAOnLocalClasspath() throws Exception {
        
    config.setHostName("does-not-exist-host"); //can't be found, will default to env
    config.init();

    String value = config.getProperty("property.1.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "custom");

    value = config.getProperty("property.2.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "value2");

  }
  
  @Test
  public void testFetchHostPropertiesByEnvironmentQAOnRemote() throws Exception {
        
    config.setEnvironmentName("DEV"); //can't be found, will default to env
    config.setHostName("does-not-exist-host"); //can't be found, will default to env
    config.init();

    String value = config.getProperty("property.1.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "value1");

    value = config.getProperty("property.2.name", String.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(value, "value2");

  }
}
