package uk.ac.ucl.twitter.search.geo.file;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class Compressor {

  /**
   * Cache to keep a reference to JSON files written.
   */
  @Inject
  private FileReference fileReference;

  /**
   * Template for execution of lzop.
   */
  private static final String COMMAND_FORMAT = "lzop -9U %s";

  /**
   * Directory from where the lzop compressor must run.
   */
  private final Path path = Paths.get(FileHandler.getSearchGeoDir());

  /**
   * Runs lzop compression over the all files collected and referenced by
   * {@link FileReference}.
   * @throws IOException If there are no files to process
   */
  public void lzoCompress() throws IOException {
    List<Path> allPaths = fileReference.getAllPaths();
    if (allPaths.isEmpty()) {
      throw new IOException("No files to process");
    }
    for (Path p : allPaths) {
      Runtime.getRuntime()
        .exec(
          String.format(COMMAND_FORMAT, p.toString()),
          null,
          path.toFile()
        );
      Logger.getLogger(Compressor.class.getName()).log(
        Level.INFO,
        String.format("File %s has been compressed.", p)
      );
    }
  }

}
