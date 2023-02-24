package uk.ac.ucl.twitter.search.geo;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.ac.ucl.twitter.search.geo.client.OAuth2Client;
import uk.ac.ucl.twitter.search.geo.client.TokenCache;

@DisplayName("Obtains OAuth2 bearer token from cache")
@ExtendWith(MockitoExtension.class)
public class OAuth2ClientFromCacheTest {

  @Mock
  private TokenCache tokenCache;

  @InjectMocks
  private OAuth2Client instance;

  @Test
    public void testGetBearerTokenFromCache() {
      when(tokenCache.existsInCache(anyString()))
          .thenReturn(true);

      when(tokenCache.getBearer(anyString()))
          .thenReturn("aToken");

      String result = instance.getBearerToken("applicationName");

      Assertions.assertThat(result).isEqualTo("aToken");
    }

}
