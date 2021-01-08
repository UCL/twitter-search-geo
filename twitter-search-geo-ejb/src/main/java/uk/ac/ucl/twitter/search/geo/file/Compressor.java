package uk.ac.ucl.twitter.search.geo.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Compressor {

  /**
   * Template for execution of lzop.
   */
  private static final String COMMAND_FORMAT = "lzop -9U %s";

  /**
   * Directory from where the lzop compressor must run.
   */
  private final Path fromPath = Paths.get(FileHandler.getSearchGeoDir());

  /**
   * Runs lzop compression over the a file collected and referenced by
   * {@link FileReference}.
   * @param path Path of the file to be compressed
   * @throws IOException If there are no files to process
   */
  public void lzoCompress(final Path path) throws IOException {
    Runtime.getRuntime()
      .exec(
        String.format(COMMAND_FORMAT, path.toString()),
        null,
        fromPath.toFile()
      );
    Logger.getLogger(Compressor.class.getName()).log(
      Level.INFO,
      String.format("File %s has been compressed.", path)
    );
  }

}
