package uk.ac.ucl.twitter.search.geo;

import com.github.tomakehurst.wiremock.WireMockServer;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

@DisplayName("Obtains OAuth2 bearer token from cache or API")
public class OAuth2ClientTest {

  @Nested
  @DisplayName("Obtains OAuth2 bearer token from cache")
  class OAuth2ClientFromCacheTest {

    @Test
    public void testGetBearerTokenFromCache(
      @Tested OAuth2Client instance,
      @Injectable TokenCache tokenCache
    ) {
      new Expectations() {{
        tokenCache.existsInCache("applicationName");
        result = true;

        tokenCache.getBearer("applicationName");
        result = "atoken";
      }};
      String result = instance.getBearerToken("applicationName");
      Assertions.assertEquals("atoken", result);
    }

  }

  /*
  Must implement steps defined in
  https://developer.twitter.com/en/docs/basics/authentication/overview/application-only

  The request must be a HTTP POST request.
  The request must include an Authorization header with the value of Basic <base64 encoded value from step 1>.
  The request must include a Content-Type header with the value of application/x-www-form-urlencoded;charset=UTF-8.
  The body of the request must be grant_type=client_credentials.
   */
  @Nested
  @DisplayName("Obtains OAuth2 bearer token from API")
  class OAuth2ClientFromApiTest {

    private final int wireMockPort = 8090;

    @BeforeEach
    public void setup() {
      System.setProperty("OAUTH2_HOST", "http://localhost:" + wireMockPort);
      System.setProperty("applicationName-KEY", "xvz1evFS4wEEPTGEFPHBog");
      System.setProperty("applicationName-SECRET", "L8qq9PZyRg6ieKGEKhZolGC0vJWLw8iEJ88DRdyOg");
    }

    @AfterEach
    public void tearDown() {
      System.clearProperty("OAUTH2_HOST");
      System.clearProperty("applicationName-KEY");
      System.clearProperty("applicationName-SECRET");
    }

    @Test
    public void testGetBearerTokenFromApi(
      @Tested OAuth2Client instance,
      @Injectable TokenCache tokenCache
    ) {
      WireMockServer wireMockServer = new WireMockServer(wireMockPort);
      wireMockServer.start();
      // setup stub
      wireMockServer.stubFor(
        post(urlPathEqualTo("/oauth2/token"))
          .willReturn(
            aResponse().withHeader("Content-Type", "application/json")
              .withStatus(200)
              .withBody(
                "{\"token_type\":\"bearer\",\"access_token\":\"AAAA%2FAAA%3DAAAAAAAA\"}"
              )
          )
      );

      instance.updateBearerToken("applicationName");
      wireMockServer.verify(
        postRequestedFor(urlPathEqualTo("/oauth2/token"))
          .withHeader(
            "Authorization",
            equalTo("Basic eHZ6MWV2RlM0d0VFUFRHRUZQSEJvZzpMOHFxOVBaeVJnNmllS0dFS2hab2xHQzB2SldMdzhpRUo4OERSZHlPZw==")
          )
          .withHeader(
            "Content-Type",
            equalTo("application/x-www-form-urlencoded;charset=UTF-8")
          )
          .withRequestBody(equalTo("grant_type=client_credentials"))
      );
      wireMockServer.stop();

      new Verifications() {{
        tokenCache.setBearer("applicationName", "AAAA%2FAAA%3DAAAAAAAA");
      }};
    }

  }

}
