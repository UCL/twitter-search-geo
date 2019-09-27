package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientConfigurationTest {

  @Test
  public void testGetFromSystemProperty() {
    String key = "propertyKey";
    String oldProperty = System.getProperty(key);
    System.setProperty(key, "propertyValue");
    String result = ClientConfiguration.getFromSystemOrEnv(key);
    Assertions.assertEquals(result, "propertyValue");
    if (null == oldProperty) {
      System.getProperties().remove(key);
    } else {
      System.setProperty(key, oldProperty);
    }
  }

  @Test
  public void testGetEmptyFromEnv() {
    String key = "envKey";
    if (System.getenv(key) != null || System.getProperty(key) != null) {
      Assertions.fail();
    }
    Assertions.assertTrue(ClientConfiguration.getFromSystemOrEnv(key).isEmpty());
  }

}
