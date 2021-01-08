package uk.ac.ucl.twitter.search.geo.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReferenceTest {

  @Test
  public void testGetAllPathsSize() {
    FileReference fileReference = new FileReference();
    fileReference.addFileReference(
      "2021-01-07_Location1", new FileHandler("Location1.json")
    );
    fileReference.addFileReference(
      "2021-01-07_Location2", new FileHandler("Location2.json")
    );
    Assertions.assertEquals(2, fileReference.getAllPaths().size());
  }
}
