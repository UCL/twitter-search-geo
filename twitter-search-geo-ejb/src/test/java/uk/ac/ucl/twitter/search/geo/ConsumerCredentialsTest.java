package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.ucl.twitter.search.geo.client.ConsumerCredentials;

public class ConsumerCredentialsTest {

  @Test
  public void testBase64EncodedCredentials() {
    String consumerKey = "xvz1evFS4wEEPTGEFPHBog";
    String consumerSecret = "L8qq9PZyRg6ieKGEKhZolGC0vJWLw8iEJ88DRdyOg";
    ConsumerCredentials instance = new ConsumerCredentials(
      consumerKey, consumerSecret
    );
    String expected = "eHZ6MWV2RlM0d0VFUFRHRUZQSEJvZzpMOHFxOVBaeVJnNmllS0dFS2hab2xHQzB2SldMdzhpRUo4OERSZHlPZw==";
    Assertions.assertEquals(expected, instance.base64EncodedCredentials());

    ConsumerCredentials instance3 = new ConsumerCredentials(null, "null");
  }

}
