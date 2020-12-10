package uk.ac.ucl.twitter.search.geo;


import jakarta.json.bind.annotation.JsonbProperty;

public class BearerTokenEntity {

  @JsonbProperty("token_type")
  private String tokenType;

  @JsonbProperty("access_token")
  private String accessToken;

  /**
   * @return the tokenType
   */
  public String getTokenType() {
    return tokenType;
  }

  /**
   * @param tokenType the tokenType to set
   */
  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  /**
   * @return the accessToken
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * @param accessToken the accessToken to set
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

}
