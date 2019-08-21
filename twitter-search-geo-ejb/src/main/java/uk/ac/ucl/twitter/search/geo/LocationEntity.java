package uk.ac.ucl.twitter.search.geo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;

/**
 * Entity class that keeps a record of the counts and last ID collected per
 * location.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Entity
@NamedQuery(
  name="LocationEntity.findByLocation",
  query="SELECT l FROM LocationEntity l WHERE l.location = :location"
)
public class LocationEntity {

  /**
   * The name of the geographical location. To be used as identifier for
   * updates.
   */
  @Enumerated(EnumType.STRING)
  private Location location;

  /**
   * Count of tweets collected.
   */
  private int count;

  /**
   * The last ID used as a reference for the collection of tweets.
   */
  private String sinceId;

  /**
   * Named query to find LocationEntity by Location name.
   */
  public static final String QUERY_FIND_BY_LOCATION =
    "LocationEntity.findByLocation";

  public static final String PARAM_LOCATION = "location";

  /**
   * No args constructor for JPA.
   */
  public LocationEntity() { }

  /**
   * Getter for the geographical location.
   * @return The name of the geographical location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Setter for the geographical location.
   * @param l A Location
   */
  public void setLocation(final Location l) {
    location = l;
  }

  /**
   * Getter for the count of tweets collected.
   * @return The count of tweets.
   */
  public int getCount() {
    return count;
  }

  /**
   * Setter for the count of tweets collected.
   * @param c The count of tweets
   */
  public void setCount(final int c) {
    count = c;
  }

  /**
   * Getter for the last ID used as a reference for the collection of tweets.
   * @return The last ID used.
   */
  public String getSinceId() {
    return sinceId;
  }

  /**
   * Setter for the last ID used as a reference for the collection of tweets.
   * @param s The last ID used
   */
  public void setSinceId(final String s) {
    sinceId = s;
  }
}
