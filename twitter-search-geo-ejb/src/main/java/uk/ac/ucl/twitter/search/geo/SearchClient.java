package uk.ac.ucl.twitter.search.geo;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Feature;

@Named
@Dependent
public class SearchClient {

  private static final String SEARCH_RESOURCE_URL =
    "https://api.twitter.com/1.1/search/tweets.json";

  /**
   * Configurable for Jersey client.
   */
  private final ClientConfig clientConfig = new ClientConfig();

  @Inject
  private OAuth2Client oAuth2Client;

  private final Feature feature = OAuth2ClientSupport.feature(
    oAuth2Client.getBearerToken("")
  );

  @PostConstruct
  public void setUp() {
    OAuth2ClientSupport.authorizationCodeGrantFlowBuilder(null, null, null);
  }

  public void search(Location location, String applicationName) {

  }

}
