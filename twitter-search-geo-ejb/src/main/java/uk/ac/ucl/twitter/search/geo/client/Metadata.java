package uk.ac.ucl.twitter.search.geo.client;

import java.util.Objects;

/**
 * Metadata from the response returned by Twitter API, used for pagination
 * and monitoring.
 *
 * @param maxId The latest Twitter ID in the response
 * @param count The number of Tweets returned
 */
public record Metadata(Long maxId, Integer count) {

  /**
   * Constructor for null checks.
   * @param maxId The latest Twitter ID in the response
   * @param count The number of Tweets returned
   */
  public Metadata {
    Objects.requireNonNull(maxId);
    Objects.requireNonNull(count);
  }
}
