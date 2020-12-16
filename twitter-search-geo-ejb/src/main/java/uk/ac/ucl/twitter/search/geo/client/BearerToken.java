package uk.ac.ucl.twitter.search.geo.client;


import jakarta.json.bind.annotation.JsonbProperty;

public class BearerToken {

  /**
   * The type of token as returned by the Twitter OAuth2 API endpoint.
   */
  @JsonbProperty("token_type")
  private String tokenType;

  /**
   * The bearer token for the application.
   */
  @JsonbProperty("access_token")
  private String accessToken;

  /**
   * @return the tokenType
   */
  public String getTokenType() {
    return tokenType;
  }

  /**
   * @param pTokenType the tokenType to set
   */
  public void setTokenType(final String pTokenType) {
    this.tokenType = pTokenType;
  }

  /**
   * @return the accessToken
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * @param pAccessToken the accessToken to set
   */
  public void setAccessToken(final String pAccessToken) {
    this.accessToken = pAccessToken;
  }

}
