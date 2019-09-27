package uk.ac.ucl.twitter.search.geo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Obtains OAuth2 bearer token from cache or API.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
@Named
@Dependent
public class OAuth2Client {

  /**
   * Host to call for obtaining OAuth2 tokens.
   */
  private final String resourceHost = ClientConfiguration
    .getFromSystemOrEnvOrElse(
      "OAUTH2_HOST",
      "https://api.twitter.com"
    );

  /**
   * Resource path for OAuth2 authentication.
   */
  private static final String OAUTH_RESOURCE_PATH = "/oauth2/token";

  /**
   * Property suffix for the consumer key. Property is expected to be in the
   * form ${APPLICATION_NAME}-KEY.
   */
  private static final String CONSUMER_KEY_SUFFIX = "-KEY";

  /**
   * Property suffix for the consumer secret. Property is expected to be in the
   * form ${APPLICATION_NAME}-SECRET.
   */
  private static final String CONSUMER_SECRET_SUFFIX = "-SECRET";

  /**
   * OAuth2 grant type for application-only authentication.
   */
  private final Form authForm = new Form("grant_type", "client_credentials");

  /**
   * An instance of a JAX-RS client invocation builder. Defaults to Jersey.
   */
  private Invocation.Builder invocationBuilder = ClientBuilder.newClient()
    .target(resourceHost)
    .path(OAUTH_RESOURCE_PATH)
    .queryParam("include_entities", "true")
    .request(MediaType.APPLICATION_JSON_TYPE);

  /**
   * Cache for storing consumer tokens.
   */
  @Inject
  private TokenCache tokenCache;

  /**
   * Obtains the bearer token associated with this application. It will attempt
   * first to return the token from the cache if it exists. Otherwise, it will
   * make a new authentication call to the API.
   * @param applicationName The name of the application
   * @return The bearer token for this application
   */
  public String getBearerToken(final String applicationName) {
    if (!tokenCache.existsInCache(applicationName)) {
      return authenticateAndSetBearerToken(applicationName);
    }
    return tokenCache.getBearer(applicationName);
  }

  /**
   * Authenticates the application on Twitter, obtaining a new bearer token and
   * storing it in the cache.
   * @param applicationName The name of the application
   */
  public void updateBearerToken(final String applicationName) {
    authenticateAndSetBearerToken(applicationName);
  }

  private String authenticateAndSetBearerToken(final String applicationName) {
    final ConsumerCredentials consumerCredentials = new ConsumerCredentials(
      ClientConfiguration.getFromSystemOrEnv(
        applicationName + CONSUMER_KEY_SUFFIX
      ),
      ClientConfiguration.getFromSystemOrEnv(
        applicationName + CONSUMER_SECRET_SUFFIX
      )
    );
    String bearerToken = authenticate(consumerCredentials);
    tokenCache.setBearer(applicationName, bearerToken);
    return bearerToken;
  }

  private String authenticate(final ConsumerCredentials consumerCredentials) {
    final Response response = invocationBuilder
      .header(
        "Authorization",
        "Basic " + consumerCredentials.base64EncodedCredentials()
      ).post(
        Entity.entity(
          authForm,
          new MediaType("application", "x-www-form-urlencoded", "UTF-8")
        )
      );
    BearerTokenEntity entity = JsonbBuilder.create().fromJson(
      response.readEntity(String.class),
      BearerTokenEntity.class
    );
    return entity.getAccessToken();
    // return response.readEntity(String.class);
  }

}
