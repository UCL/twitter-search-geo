package uk.ac.ucl.twitter.search.geo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import uk.ac.ucl.twitter.search.geo.client.OAuth2Client;
import uk.ac.ucl.twitter.search.geo.client.TokenCache;

/*
  Must implement steps defined in
  https://developer.twitter.com/en/docs/basics/authentication/overview/application-only

  The request must be a HTTP POST request.
  The request must include an Authorization header with the value of Basic <base64 encoded value from step 1>.
  The request must include a Content-Type header with the value of application/x-www-form-urlencoded;charset=UTF-8.
  The body of the request must be grant_type=client_credentials.
   */
@DisplayName("Obtains OAuth2 bearer token from API")
@ExtendWith(MockitoExtension.class)
@WireMockTest(httpPort = 8090)
class OAuth2ClientFromApiTest {

  @InjectMocks
  private OAuth2Client instance;

  @Mock
  private TokenCache tokenCache;

  @BeforeAll
  public static void setup() {
    System.setProperty("OAUTH2_HOST", "http://localhost:8090");
    System.setProperty("applicationName-KEY", "xvz1evFS4wEEPTGEFPHBog");
    System.setProperty("applicationName-SECRET", "L8qq9PZyRg6ieKGEKhZolGC0vJWLw8iEJ88DRdyOg");
  }

  @AfterAll
  public static void tearDown() {
    System.clearProperty("OAUTH2_HOST");
    System.clearProperty("applicationName-KEY");
    System.clearProperty("applicationName-SECRET");
  }

  @Test
  public void testGetBearerTokenFromApi() {
    WireMock.stubFor(
        WireMock.post(WireMock.urlPathEqualTo("/oauth2/token"))
            .willReturn(
                WireMock.aResponse().withHeader("Content-Type", "application/json")
                    .withStatus(200)
                    .withBody(
                        "{\"token_type\":\"bearer\",\"access_token\":\"AAAA%2FAAA%3DAAAAAAAA\"}")));

    instance.updateBearerToken("applicationName");

    WireMock.verify(
        WireMock.postRequestedFor(WireMock.urlPathEqualTo("/oauth2/token"))
            .withHeader(
                "Authorization",
                WireMock.equalTo(
                    "Basic eHZ6MWV2RlM0d0VFUFRHRUZQSEJvZzpMOHFxOVBaeVJnNmllS0dFS2hab2xHQzB2SldMdzhpRUo4OERSZHlPZw=="))
            .withHeader(
                "Content-Type",
                WireMock.equalTo("application/x-www-form-urlencoded;charset=UTF-8"))
            .withRequestBody(WireMock.equalTo("grant_type=client_credentials")));

    ArgumentCaptor<String> param0 = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> param1 = ArgumentCaptor.forClass(String.class);

    Mockito.verify(tokenCache).setBearer(param0.capture(), param1.capture());

    Assertions.assertThat(param0.getValue()).isEqualTo("applicationName");
    Assertions.assertThat(param1.getValue()).isEqualTo("AAAA%2FAAA%3DAAAAAAAA");

  }

}
