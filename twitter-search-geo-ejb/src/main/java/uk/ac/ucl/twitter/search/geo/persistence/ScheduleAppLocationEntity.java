package uk.ac.ucl.twitter.search.geo.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * Entity class containing schedule information for API calls based on location.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
@Entity
@NamedQuery(
  name = "ScheduleAppLocationEntity.findAll",
  query = "SELECT l FROM ScheduleAppLocationEntity l"
)
@Table(name = "SCHEDULE_APP_LOCATION")
public class ScheduleAppLocationEntity {

  /**
   * The name of the application, used as authentication context.
   */
  @Column(name = "APP_NAME")
  private String appName;

  /**
   * The schedule for an API call in CRON format.
   */
  @Column(name = "INTERVAL_MINUTES")
  private int intervalMinutes;

  /**
   * The delay in seconds to add to the schedule.
   */
  @Column(name = "DELAY_SECONDS")
  private int delaySeconds;

  /**
   * The name of the geographical location. To be used as identifier for
   * updates.
   */
  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "GEOLOCATION")
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

  /**
   * Getter for the delaySeconds field.
   * @return The delay in seconds to add to the schedule
   */
  public int getDelaySeconds() {
    return delaySeconds;
  }

}
