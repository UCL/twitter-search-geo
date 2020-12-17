package uk.ac.ucl.twitter.search.geo.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * Entity class that keeps a record of the counts and last ID collected per
 * location.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Entity
@NamedQuery(
  name = "LocationEntity.findByLocation",
  query = "SELECT l FROM LocationEntity l WHERE l.location = :location"
)
@Table(name = "LOCATION_TRACKING")
public class LocationEntity {

  /**
   * The name of the geographical location. To be used as identifier for
   * updates.
   */
  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "GEOLOCATION")
  private Location location;

  /**
   * Count of tweets collected.
   */
  @Column(name = "TWEET_COUNT")
  private int count = 0;

  /**
   * The last ID used as a reference for the collection of tweets.
   */
  @Column(name = "SINCE_ID")
  private long sinceId = 0;

  /**
   * Named query to find LocationEntity by Location name.
   */
  public static final String QUERY_FIND_BY_LOCATION =
    "LocationEntity.findByLocation";

  /**
   * Name of the parameter in findByLocation typed query.
   */
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
  public long getSinceId() {
    return sinceId;
  }

  /**
   * Setter for the last ID used as a reference for the collection of tweets.
   * @param s The last ID used
   */
  public void setSinceId(final long s) {
    sinceId = s;
  }
}
