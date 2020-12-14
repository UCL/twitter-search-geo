package uk.ac.ucl.twitter.search.geo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

/**
 * Entity class containing schedule information for API calls based on location.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
@Entity
@NamedQuery(
  name="ScheduleAppLocationEntity.findAll",
  query="SELECT l FROM ScheduleAppLocationEntity l"
)
public class ScheduleAppLocationEntity {

  /**
   * The name of the application, used as authentication context.
   */
  private String appName;

  /**
   * The schedule for an API call in CRON format.
   */
  private int intervalMinutes;

  /**
   * The name of the geographical location. To be used as identifier for
   * updates.
   */
  @Id
  @Enumerated(EnumType.STRING)
  private Location location;

  /**
   * Named query to find ScheduleAppLocationEntity.
   */
  public static final String QUERY_FIND_ALL =
    "ScheduleAppLocationEntity.findAll";

  /**
   * No args constructor for JPA.
   */
  public ScheduleAppLocationEntity() { }

  /**
   * Getter for the appName field.
   * @return The name of the application
   */
  public String getAppName() {
    return appName;
  }

  /**
   * Getter for the cronSchedule field.
   * @return A String representing the schedule in CRON format
   */
  public int getIntervalMinutes() {
    return intervalMinutes;
  }

  /**
   * Getter for the geographical location.
   * @return The name of the geographical location
   */
  public Location getLocation() {
    return location;
  }

}
