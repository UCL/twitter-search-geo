package uk.ac.ucl.twitter.search.geo.client;

/**
 * Container for the status data returned from Twitter API.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
public class StatusData {

  /**
   * The status node, as a String.
   */
  private String statuses = "";

  /**
   * Container for the metadata in the response returned from Twitter API.
   */
  private final Metadata metaData = new Metadata();

  /**
   * Getter for statuses.
   * @return The status node as a String
   */
  public String getStatuses() {
    return statuses;
  }

  /**
   * Setter for statuses.
   * @param s The status node as a String
   */
  public void setStatuses(final String s) {
    statuses = s;
  }

  /**
   * Getter for metadata.
   * @return The metadata contained in the response returned by Twitter API.
   */
  public Metadata getMetaData() {
    return metaData;
  }

}
