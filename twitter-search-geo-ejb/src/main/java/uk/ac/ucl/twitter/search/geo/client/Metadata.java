package uk.ac.ucl.twitter.search.geo.client;

/**
 * Metadata from the response returned by Twitter API, used for pagination
 * and monitoring.
 */
public class Metadata {

  /**
   * The latest Twitter ID in the response.
   */
  private long maxId = 0L;

  /**
   * The number of Tweets returned.
   */
  private int count = 0;

  /**
   * Getter for maxId variable.
   *
   * @return The latest Twitter ID contained in the response
   */
  public long getMaxId() {
    return maxId;
  }

  /**
   * Setter for maxId variable.
   *
   * @param l The latest Twitter ID contained in the response
   */
  public void setMaxId(final long l) {
    maxId = l;
  }

  /**
   * Getter for count variable.
   *
   * @return The number of Tweets returned.
   */
  public int getCount() {
    return count;
  }

  /**
   * Setter for count variable.
   *
   * @param c The number of Tweets returned.
   */
  public void setCount(final int c) {
    count = c;
  }

}
