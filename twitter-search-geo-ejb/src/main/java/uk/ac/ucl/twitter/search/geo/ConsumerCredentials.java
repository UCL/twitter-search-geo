package uk.ac.ucl.twitter.search.geo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Application consumer credentials for calling Twitter API with
 * application-only authentication. It stores the consumer secret as a byte
 * array for improved security.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
public class ConsumerCredentials {

  /**
   * The consumer key.
   */
  private final String consumerKey;

  /**
   * The consumer secret, as byte array for security.
   */
  private final byte[] consumerSecret;

  /**
   * Creates a new instance.
   * @param key The consumer key
   * @param secret The consumer secret
   */
  public ConsumerCredentials(final String key, final String secret) {
    this.consumerKey = key;
    this.consumerSecret = secret.getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Generates the token credentials for authentication on Twitter API.
   * @return The Base64-encoded credentials
   */
  public String base64EncodedCredentials() {
    return Base64.getEncoder().encodeToString(
      getJoinedRfc1738Credentials().getBytes(StandardCharsets.UTF_8)
    );
  }

  private String getJoinedRfc1738Credentials() {
    return Stream.of(
      consumerKey, new String(consumerSecret, StandardCharsets.UTF_8)
    ).map(this::rfc1738Encode)
     .collect(Collectors.joining(":"));
  }

  private String rfc1738Encode(final String input) {
    try {
      return URLEncoder.encode(input, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      return null;
    }
  }
}
