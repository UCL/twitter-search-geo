package uk.ac.ucl.twitter.search.geo.file;

import jakarta.inject.Inject;

/**
 * Factory methods for FileHandler.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
public final class FileHandlerFactory {

  /**
   * Internal store of FileHandler instances. One instance per date per
   * location.
   */
  @Inject
  private FileReference fileReference;

  private FileHandlerFactory() { }

  /**
   * Static constructor method.
   * @return A new instance of FileHandlerFactory
   */
  public static FileHandlerFactory newInstance() {
    return new FileHandlerFactory();
  }

  /**
   * Obtains an instance of FileHandler. Restricted to one instance per date
   * per location.
   * @param todayAndLocation A String combining the date and location in the
   *                         format YYYY-MM-DD_Location
   * @return An instance of FileHandler
   */
  public FileHandler createFileHandler(final String todayAndLocation) {
    if (fileReference.hasFile(todayAndLocation)) {
      return fileReference.get(todayAndLocation);
    } else {
      FileHandler fileHandler = new FileHandler(todayAndLocation);
      fileReference.addFileReference(todayAndLocation, fileHandler);
      return fileHandler;
    }
  }

}
