package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.json.bind.JsonbBuilder;

public class BearerTokenEntityTest {

  @Test
  public void testFromJsonToObject() {
    String jsonResponse = "{\"token_type\":\"bearer\",\"access_token\":\"AAAA%2FAAA%3DAAAAAAAA\"}";
    BearerTokenEntity entity = JsonbBuilder.create()
      .fromJson(jsonResponse, BearerTokenEntity.class);
    Assertions.assertEquals("bearer", entity.getTokenType());
    Assertions.assertEquals("AAAA%2FAAA%3DAAAAAAAA", entity.getAccessToken());
  }

}
