package uk.ac.ucl.twitter.search.geo;

import java.io.Serializable;

/**
 * Additional configuration information for TimerConfig.
 *
 * @author David Guzman
 * @since 1.0
 */
public class AppLocationTimerConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The name of the geographical location. To be used as identifier for
   * updates.
   */
  private final Location location;

  /**
   * The name of the application, used as authentication context.
   */
  private final String applicationName;

  /**
   * Constructor.
   * @param l The Location to collect the data from when the timer is triggered.
   * @param a The name of the application.
   */
  public AppLocationTimerConfig(final Location l, final String a) {
    location = l;
    applicationName = a;
  }

  /**
   * Getter for the location of the timer event.
   * @return The geographical location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Getter for the name of the application.
   * @return The name of the application
   */
  public String getApplicationName() {
    return applicationName;
  }

}
