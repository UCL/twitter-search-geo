package uk.ac.ucl.twitter.search.geo.file;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Creates and locates instances of FileHandler.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
@Dependent
public final class FileHandlerLocator {

  /**
   * Regular expression pattern for an ISO date.
   */
  private static final String STARTS_REGEX = "^\\d{4}-\\d{2}-\\d{2}_.*";

  /**
   * Internal store of FileHandler instances. One instance per date per
   * location.
   */
  @Inject
  private FileReference fileReference;

  /**
   * Obtains all file handlers with names that do not start with a ISO date
   * specified by isoDate.
   */
  private final Function<String, List<FileHandler>> getFileHandlers =
    (String isoDate) -> fileReference.getAllPaths().stream()
        .map(Path::getFileName)
        .map(Path::toString)
        .filter(s -> s.matches(STARTS_REGEX))
        .filter(s -> !s.startsWith(isoDate))
        .map(s -> fileReference.get(s))
        .collect(Collectors.toList());

  /**
   * Obtains an instance of FileHandler. Restricted to one instance per date
   * per location.
   * @param todayAndLocation A String combining the date and location in the
   *                         format YYYY-MM-DD_Location
   * @return An instance of FileHandler
   */
  public FileHandler obtain(final String todayAndLocation) {
    if (fileReference.hasFile(todayAndLocation)) {
      return fileReference.get(todayAndLocation);
    } else {
      FileHandler fileHandler = new FileHandler(todayAndLocation);
      fileReference.addFileReference(todayAndLocation, fileHandler);
      return fileHandler;
    }
  }

  /**
   * Calls the closure of FileHandler where the date part of the file name
   * does not match the date provided.
   * @param isoDate A String with the date in YYYY-MM-DD format
   */
  public void close(final String isoDate) {
    List<FileHandler> filteredFileHandler = getFileHandlers.apply(isoDate);
    for (FileHandler f : filteredFileHandler) {
      Logger.getLogger(FileHandlerLocator.class.getName())
        .log(Level.INFO,
          String.format("Closing file %s%n", f.getPath().toString()));
      try {
        f.closeFile();
      } catch (IOException e) {
        Logger.getLogger(FileHandlerLocator.class.getName())
          .log(
            Level.WARNING,
            String.format("Could not close FileHandler for %s", f.toString())
          );
      }
    }
  }

  /**
   * Compresses the file referenced by a FileHandler where the date part of
   * the file does not match the date provided.
   * @param isoDate A String with the date in YYYY-MM-DD format
   */
  public void compress(final String isoDate) {
    List<FileHandler> filteredFileHandler = getFileHandlers.apply(isoDate);
    for (FileHandler f : filteredFileHandler) {
      Compressor compressor = new Compressor();
      try {
        compressor.lzoCompress(f.getPath());
      } catch (IOException e) {
        Logger.getLogger(FileHandlerLocator.class.getName())
          .log(
            Level.WARNING,
            String.format("Could not compress FileHandler for %s", f.toString())
          );
      }
    }
  }

}
