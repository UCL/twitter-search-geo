package uk.ac.ucl.twitter.search.geo;

import com.github.tomakehurst.wiremock.WireMockServer;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.ac.ucl.twitter.search.geo.client.OAuth2Client;
import uk.ac.ucl.twitter.search.geo.client.SearchClient;
import uk.ac.ucl.twitter.search.geo.file.FileHandlerFactory;
import uk.ac.ucl.twitter.search.geo.persistence.EntityAccess;
import uk.ac.ucl.twitter.search.geo.persistence.Location;
import uk.ac.ucl.twitter.search.geo.persistence.LocationEntity;

import java.io.ByteArrayOutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

@DisplayName("Calls the search API")
public class SearchClientTest {

  private static Logger log = Logger.getLogger(SearchClient.class.getName());

  @Nested
  @DisplayName("The API returns 200/OK")
  class SearchClient200Test {

    private final int wireMockPort = 8092;

    @BeforeEach
    public void setup() {
      System.setProperty("SEARCH_HOST", "http://localhost:" + wireMockPort);
    }

    @AfterEach
    public void tearDown() {
      System.clearProperty("SEARCH_HOST");
    }

    @Test
    public void testRunSearchWhenOk(
      @Tested SearchClient instance,
      @Injectable OAuth2Client oAuth2Client,
      @Injectable EntityAccess entityAccess,
      @Mocked FileHandlerFactory fileHandlerFactory
    ) {
      WireMockServer wireMockServer = new WireMockServer(wireMockPort);
      wireMockServer.start();
      // setup stub
      Location loc = Location.Aberdeen;
      wireMockServer.stubFor(
        get(urlPathEqualTo("/1.1/search/tweets.json"))
          .willReturn(
            aResponse().withHeader("Content-Type", "application/json")
              .withStatus(200)
              .withBody("{\"response\":\"A response\"}")
          )
      );

      LocationEntity locationEntityStub = new LocationEntity();
      locationEntityStub.setCount(10);
      locationEntityStub.setLocation(Location.Aberdeen);
      locationEntityStub.setSinceId(0L);
      new Expectations() {{
        entityAccess.findLocationEntityByLocation(Location.Aberdeen);
        result = locationEntityStub;

        oAuth2Client.getBearerToken("applicationName");
        result = "aToken";
      }};
      instance.runSearch(Location.Aberdeen, "applicationName");

      wireMockServer.verify(
        getRequestedFor(urlPathEqualTo("/1.1/search/tweets.json"))
          .withHeader(
            "Authorization",
            equalTo("Bearer aToken")
          )
          .withQueryParam("query", matching("since:.*"))
          .withQueryParam("geocode", equalTo(loc.getLatitude() + "," + loc.getLongitude() + "," + loc.getRadius()))
          .withQueryParam("count", equalTo("100"))
          .withQueryParam("since_id", equalTo("0"))
      );

      new Verifications() {{
        entityAccess.updateLocationEntity(locationEntityStub);
        times = 1;
      }};

      wireMockServer.stop();
    }

  }

  @Nested
  @DisplayName("The API does not return 200/OK")
  class SearchClientNot200Test {

    private final int wireMockPort = 8092;

    @BeforeEach
    public void setup() {
      System.setProperty("SEARCH_HOST", "http://localhost:" + wireMockPort);
    }

    @AfterEach
    public void tearDown() {
      System.clearProperty("SEARCH_HOST");
    }

    @Test
    public void testRunSearchWhenNotOk(
      @Tested SearchClient instance,
      @Injectable OAuth2Client oAuth2Client,
      @Injectable EntityAccess entityAccess
    ) {
      WireMockServer wireMockServer = new WireMockServer(wireMockPort);
      wireMockServer.start();

      // setup stub
      Location loc = Location.Aberdeen;
      wireMockServer.stubFor(
        get(urlPathEqualTo("/1.1/search/tweets.json"))
          .willReturn(
            aResponse().withHeader("Content-Type", "application/json")
              .withStatus(400)
          )
      );

      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      Handler[] handlers = log.getParent().getHandlers();
      StreamHandler streamHandler = new StreamHandler(byteArrayOutputStream, handlers[0].getFormatter());
      log.addHandler(streamHandler);

      LocationEntity locationEntityStub = new LocationEntity();
      locationEntityStub.setCount(10);
      locationEntityStub.setLocation(Location.Aberdeen);
      locationEntityStub.setSinceId(0L);

      new Expectations() {{
        entityAccess.findLocationEntityByLocation(Location.Aberdeen);
        result = locationEntityStub;

        oAuth2Client.getBearerToken("applicationName");
        result = "aToken";
      }};

      instance.runSearch(Location.Aberdeen, "applicationName");

      streamHandler.flush();
      String collectedLog = new String(byteArrayOutputStream.toByteArray());

      wireMockServer.verify(
        getRequestedFor(urlPathEqualTo("/1.1/search/tweets.json"))
        .withHeader(
          "Authorization",
          equalTo("Bearer aToken")
        )
        .withQueryParam("query", matching("since:.*"))
        .withQueryParam("geocode", equalTo(loc.getLatitude() + "," + loc.getLongitude() + "," + loc.getRadius()))
        .withQueryParam("count", equalTo("100"))
        .withQueryParam("since_id", equalTo("0"))
      );

      wireMockServer.stop();

      Assertions.assertTrue(
        collectedLog.contains("INFO: Call to API did not return OK. Status code: 400")
      );
    }

  }

}
