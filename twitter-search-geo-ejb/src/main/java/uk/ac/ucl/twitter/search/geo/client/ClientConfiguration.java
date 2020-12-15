package uk.ac.ucl.twitter.search.geo.client;

import java.nio.charset.StandardCharsets;

/**
 * Utilities for passing or reading configuration properties.
 *
 * @author David Guzman
 * @since 1.0
 */
public interface ClientConfiguration {

  /**
   * Obtains the value for a property key, first from the list of system
   * properties, if it can be found then it attempts to find the property
   * from the environment.
   * @param key The name of the property.
   * @return    The value of the property.
   */
  static String getFromSystemOrEnv(final String key) {
    String value = System.getProperty(key, "");
    if (value.isEmpty()) {
      value = System.getenv(key);
    }
    return (value != null) ? value : "";
  }

  /**
   * Obtains the value for a property key, first from the list of system
   * properties, if it can be found then it attempts to find the property
   * from the environment. If both fail, then it returns the default value
   * provided.
   * @param key           The name of the property.
   * @param defaultValue  The default value for the property.
   * @return              The value of the property.
   */
  static String getFromSystemOrEnvOrElse(
    final String key, final String defaultValue
  ) {
    String value = System.getProperty(key, "");
    if (value.isEmpty()) {
      value = System.getenv(key);
    }
    return (value != null) ? value : defaultValue;
  }

  static byte[] getPropertyFromSystem(final String key) {
    return System.getProperty(key)
      .getBytes(StandardCharsets.UTF_8);
  }

}
