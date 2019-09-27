package uk.ac.ucl.twitter.search.geo;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * Application cache to store the OAuth2 bearer tokens to be used in
 * application-only authentication on Twitter API.
 *
 * @author David Guzman
 * @since 1.0
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Lock(LockType.READ)
public class TokenCache {

  /**
   * Cache for the bearer token, it uses the application name as a key. This
   * application name should be the same one used for registering the consumer
   * credentials (key and secret).
   */
  private final Map<String, String> appBearer = new HashMap<>();

  /**
   * Default value of a bearer token to be returned when the application name
   * is not present in the cache.
   */
  private static final String EMPTY_BEARER = "";

  /**
   * Returns the cached bearer token for an application. Defaults to an empty
   * String when the application name is not present in the cache.
   * @param applicationName The name of the application
   * @return The bearer token
   */
  public String getBearer(final String applicationName) {
    if (appBearer.containsKey(applicationName)) {
      return appBearer.get(applicationName);
    }
    return EMPTY_BEARER;
  }

  /**
   * Updates the value of the bearer token for a particular application.
   * @param applicationName The name of the application
   * @param newBearer The new bearer token.
   */
  @Lock(LockType.WRITE)
  public void setBearer(final String applicationName, final String newBearer) {
    appBearer.put(applicationName, newBearer);
  }

  /**
   * Evaluates whether there is an entry for a specific application name.
   * @param applicationName The name of the application used as a key
   * @return true if there is an entry, false otherwise
   */
  public boolean existsInCache(final String applicationName) {
    return appBearer.containsKey(applicationName);
  }

}
