package uk.ac.ucl.twitter.search.geo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named
@Dependent
public class SearchClient {

  private final String searchResourceHost = ClientConfiguration
    .getFromSystemOrEnvOrElse(
      "SEARCH_HOST",
      "https://api.twitter.com"
    );

  private static final String SEARCH_RESOURCE_PATH =  "/1.1/search/tweets.json";

  private static final int NUMBER_OF_TWEETS = 100;

  private final WebTarget webTarget = ClientBuilder.newClient()
    .target(searchResourceHost)
    .path(SEARCH_RESOURCE_PATH);

  @Inject
  private OAuth2Client oAuth2Client;

  @Inject
  private EntityAccess entityAccess;

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
      FileHandler fileHandler = FileHandler.createFileHandler(loc);
      long maxId = fileHandler.writeStatuses(response.readEntity(String.class));
      locationEntity.setSinceId(maxId);
      entityAccess.updateLocationEntity(locationEntity);
    } else {
      // log or throw
    }
  }

}
