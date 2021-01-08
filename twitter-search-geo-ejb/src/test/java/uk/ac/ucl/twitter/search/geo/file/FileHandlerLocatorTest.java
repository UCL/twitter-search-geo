package uk.ac.ucl.twitter.search.geo.file;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandlerLocatorTest {

  final @Tested FileHandlerLocator instance = new FileHandlerLocator();

  @Test
  public void testClose(@Injectable FileReference fileReference, @Mocked FileHandler fileHandler) throws IOException {
    List<Path> allPaths = new ArrayList<>();
    allPaths.add(
      Paths.get(System.getProperty("java.io.tmpdir"), "2021-01-06_Location1")
    );
    allPaths.add(
      Paths.get(System.getProperty("java.io.tmpdir"), "2021-01-06_Location2")
    );
    new Expectations() {{
      fileReference.getAllPaths();
      result = allPaths;
    }};
    instance.close("2021-01-07");
    new Verifications() {{
      fileHandler.closeFile();
      times = 2;
    }};
  }

}
