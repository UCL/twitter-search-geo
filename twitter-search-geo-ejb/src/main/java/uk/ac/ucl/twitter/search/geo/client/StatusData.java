package uk.ac.ucl.twitter.search.geo.client;

import java.util.Objects;

/**
 * Container for the status data returned from Twitter API.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 *
 * @param statuses The status node as a String
 * @param metadata The metadata in the response returned from Twitter API
 */
public record StatusData(String statuses, Metadata metadata) {

  /**
   * Constructor to enforce null checks.
   * @param statuses The status node as a String
   * @param metadata The metadata in the response returned from Twitter API
   */
  public StatusData {
    Objects.requireNonNull(statuses);
    Objects.requireNonNull(metadata);
  }

}
