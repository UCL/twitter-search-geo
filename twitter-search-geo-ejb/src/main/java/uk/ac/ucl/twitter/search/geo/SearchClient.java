package uk.ac.ucl.twitter.search.geo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;

/**
 * Queries Twitter standard search API to retrieve a collection of tweets
 * based on geographic locations.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
@Named
@Dependent
public class SearchClient {

  /**
   * Host of the standard search api. Read from a configuration property to
   * facilitate testing.
   */
  private final String searchResourceHost = ClientConfiguration
    .getFromSystemOrEnvOrElse(
      "SEARCH_HOST",
      "https://api.twitter.com"
    );

  /**
   * Path to the standard search resource.
   */
  private static final String SEARCH_RESOURCE_PATH =  "/1.1/search/tweets.json";

  /**
   * Number of tweets to return per page.
   */
  private static final int NUMBER_OF_TWEETS = 100;

  /**
   * An instance of WebTarget pointing to the standard search resource.
   */
  private final WebTarget webTarget = ClientBuilder.newClient()
    .target(searchResourceHost)
    .path(SEARCH_RESOURCE_PATH);

  /**
   * Provides the OAuth2 bearer token.
   */
  @Inject
  private OAuth2Client oAuth2Client;

  /**
   * Provides access to query and update entities in database.
   */
  @Inject
  private EntityAccess entityAccess;

  /**
   * Runs a query on Twitter's search API. Results are passed to FileHandler
   * to be persisted as text files and the max ID in the collection of tweets
   * saved in the database for pagination of subsequent queries.
   * @param loc             The geographic location.
   * @param applicationName The name of the application. Used as authentication
   *                        context.
   */
  public void runSearch(final Location loc, final String applicationName) {
    final LocationEntity locationEntity = entityAccess
      .findLocationEntityByLocation(loc);
    final String sinceDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    final Response response = webTarget
      .queryParam(
        "query",
        "since:" + sinceDate
      ).queryParam(
        "geocode",
        loc.getLatitude() + "," + loc.getLongitude() + "," + loc.getRadius()
      )
      .queryParam("count", NUMBER_OF_TWEETS)
      .queryParam("since_id", locationEntity.getSinceId())
      .request(MediaType.APPLICATION_JSON_TYPE)
      .header(
        "Authorization",
        "Bearer " + oAuth2Client.getBearerToken(applicationName)
      ).get();
    if (Response.Status.OK.getStatusCode() == response.getStatus()) {
      FileHandler fileHandler = FileHandler.createFileHandler(
        sinceDate + "_" + loc.name()
      );
      try {
        StatusData.Metadata metadata = fileHandler.writeStatuses(
          response.readEntity(String.class)
        );
        locationEntity.setSinceId(metadata.getMaxId());
        locationEntity.setCount(metadata.getCount());
        entityAccess.updateLocationEntity(locationEntity);
      } catch (IOException e) {
        getLogger(SearchClient.class.getName())
          .log(Level.SEVERE, "Could not write response to file.", e);
      }
    } else {
      getLogger(SearchClient.class.getName())
        .log(
          Level.INFO,
          "Call to API did not return OK. Status code: {0}",
          response.getStatus()
        );
    }
  }

}
