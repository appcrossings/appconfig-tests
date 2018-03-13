package com.appcrossings.configtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.appcrossings.config.spring.ConfigrdPropertyPlaceholderConfigurer;

@ContextConfiguration(classes = {IntegrationTestApplicationContext.class})
public class RawPropertiesITCase extends AbstractTestNGSpringContextTests {

  @Autowired
  private ConfigrdPropertyPlaceholderConfigurer config;

  @Autowired
  private SampleClass sample;

  @Test
  public void reloadClassProperties() {

    Assert.assertNull(config.getProperty("property.1.name", String.class));
    Assert.assertNull(config.getProperty("property.2.name", String.class));
    Assert.assertNull(config.getProperty("property.4.name", String.class));

    Assert.assertEquals(sample.getSomeValue1(), "none");
    Assert.assertEquals(sample.getSomeValue2(), "none");
    Assert.assertEquals(sample.getSomeValue4(), "none");

    config.setHostName("remote-host");
    config.setEnvironment("QA");
    config.traverseClasspath(false);
    config.reload();

    Assert.assertEquals(config.getProperty("property.1.name", String.class), "value1");
    Assert.assertEquals(config.getProperty("property.2.name", String.class), "value2");
    Assert.assertNull(config.getProperty("property.4.name", String.class));

    Assert.assertEquals(sample.getSomeValue1(), "value1");
    Assert.assertEquals(sample.getSomeValue2(), "value2");
    Assert.assertEquals(sample.getSomeValue4(), "none");

    config.setHostName("not-exists-remote-host");
    config.setEnvironment("QA");
    config.traverseClasspath(false);
    config.reload();

    Assert.assertEquals(config.getProperty("property.1.name", String.class), "custom");
    Assert.assertEquals(config.getProperty("property.2.name", String.class), "value2");
    Assert.assertEquals(config.getProperty("property.4.name", String.class), "custom-custom2");

    Assert.assertEquals(sample.getSomeValue1(), "custom");
    Assert.assertEquals(sample.getSomeValue2(), "value2");
    Assert.assertEquals(sample.getSomeValue4(), "custom-custom2");

  }

  @Test
  public void testNoPropertiesPastConfigRootLoaded() throws Exception {

    config.setHostName("remote-host");
    config.setEnvironment("QA");
    config.traverseClasspath(false);
    config.reload();

    Assert.assertNull(config.getProperty("property.5.name", String.class));
    Assert.assertNull(config.getProperty("log.root.level", String.class));
    Assert.assertNull(config.getProperty("property.6.name", String.class));
    Assert.assertNull(config.getProperty("server.port", Integer.class));
    Assert.assertNull(config.getProperty("filesystem.root", String.class));

  }

  @Test
  public void testMergeRemoteAndLocalClasspathProperties() throws Exception {

    config.setHostName("remote-host");
    config.setEnvironment("QA");
    config.traverseClasspath(true);
    config.reload();

    Assert.assertNotNull(config.getProperty("property.5.name", String.class));
    Assert.assertNotNull(config.getProperty("log.root.level", String.class));
    Assert.assertNotNull(config.getProperty("property.6.name", String.class));

  }
}
