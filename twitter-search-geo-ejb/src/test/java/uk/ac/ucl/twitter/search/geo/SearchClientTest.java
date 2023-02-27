package uk.ac.ucl.twitter.search.geo;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.ac.ucl.twitter.search.geo.client.OAuth2Client;
import uk.ac.ucl.twitter.search.geo.client.SearchClient;
import uk.ac.ucl.twitter.search.geo.file.FileHandler;
import uk.ac.ucl.twitter.search.geo.file.FileHandlerLocator;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("Calls the search API")
@ExtendWith(MockitoExtension.class)
public class SearchClientTest {

  private static Logger log = Logger.getLogger(SearchClient.class.getName());

  @Nested
  @DisplayName("The API returns 200/OK")
  class SearchClient200Test {

    private static final int wireMockPort = 8092;

    @BeforeAll
    public static void setup() {
      System.setProperty("SEARCH_HOST", "http://localhost:" + wireMockPort);
    }

    @AfterAll
    public static void tearDown() {
      System.clearProperty("SEARCH_HOST");
    }

    @Mock
    private OAuth2Client oAuth2Client;

    @Mock
    private EntityAccess entityAccess;

    @Mock
    private FileHandlerLocator fileHandlerLocator;

    @InjectMocks
    private SearchClient instance;

    @Test
    public void testRunSearchWhenOk() {
      WireMockServer wireMockServer = new WireMockServer(wireMockPort);
      wireMockServer.start();
      // setup stub
      Location loc = Location.Aberdeen;
      wireMockServer.stubFor(
        get(urlPathEqualTo("/1.1/search/tweets.json"))
          .willReturn(
            aResponse().withHeader("Content-Type", "application/json")
              .withStatus(200)
              //.withBody("{\"statuses\":\"A response\"}")
              .withBody("""
              {
                \"statuses\": [
                  {
                    \"text\": \"a Text\"
                  }
                ],
                \"search_metadata\": {
                  \"max_id\": 0,
                  \"count\": 1
                }
              }
              """)
          )
      );

      LocationEntity locationEntityStub = new LocationEntity();
      locationEntityStub.setCount(10);
      locationEntityStub.setLocation(Location.Aberdeen);
      locationEntityStub.setSinceId(0L);

      when(entityAccess.findLocationEntityByLocation(Location.Aberdeen))
          .thenReturn(locationEntityStub);

      when(oAuth2Client.getBearerToken(anyString()))
          .thenReturn("aToken");

      when(fileHandlerLocator.obtain(anyString()))
          .thenReturn(new FileHandler("2020-02-24_Aberdeen"));

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

      Mockito.verify(entityAccess, times(1)).updateLocationEntity(locationEntityStub);

      wireMockServer.stop();
    }

  }

  @Nested
  @DisplayName("The API does not return 200/OK")
  class SearchClientNot200Test {

    private static final int wireMockPort = 8092;

    @BeforeAll
    public static void setup() {
      System.setProperty("SEARCH_HOST", "http://localhost:" + wireMockPort);
    }

    @AfterAll
    public static void tearDown() {
      System.clearProperty("SEARCH_HOST");
    }

    @Mock
    private OAuth2Client oAuth2Client;

    @Mock
    private EntityAccess entityAccess;

    @InjectMocks
    private SearchClient instance;

    @Test
    public void testRunSearchWhenNotOk() {
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

      when(entityAccess.findLocationEntityByLocation(Location.Aberdeen)).thenReturn(locationEntityStub);

      when(oAuth2Client.getBearerToken(anyString())).thenReturn("aToken");

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

      Assertions.assertThat(collectedLog.contains("INFO: Call to API did not return OK. Status code: 400")).isTrue();

    }

  }

}
