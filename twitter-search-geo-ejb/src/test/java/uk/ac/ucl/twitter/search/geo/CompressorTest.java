package uk.ac.ucl.twitter.search.geo;

import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class CompressorTest {

  @Test
  public void testLzoCompressWhenNoFiles(
    @Tested Compressor instance,
    @Injectable FileReference fileReference
  ) {
    Assertions.assertThrows(IOException.class, () -> instance.lzoCompress());
  }

}
