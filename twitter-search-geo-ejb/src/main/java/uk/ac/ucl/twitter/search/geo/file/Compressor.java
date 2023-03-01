package uk.ac.ucl.twitter.search.geo.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Compressor {

  /**
   * Directory from where the lzop compressor must run.
   */
  private final Path fromPath = Paths.get(FileHandler.getSearchGeoDir());

  /**
   * Template for execution of lzop.
   */
  private final String[] command = new String[] {"lzop", "-9U"};

  /**
   * Runs lzop compression over the a file collected and referenced by
   * {@link FileReference}.
   * @param path Path of the file to be compressed
   * @throws IOException If there are no files to process
   */
  public void lzoCompress(final Path path) throws IOException {
    command[2] = path.toString();
    Runtime.getRuntime()
      .exec(
        command,
        null,
        fromPath.toFile()
      );
    Logger.getLogger(Compressor.class.getName()).log(
      Level.INFO,
      String.format("File %s has been compressed.", path)
    );
  }

}
