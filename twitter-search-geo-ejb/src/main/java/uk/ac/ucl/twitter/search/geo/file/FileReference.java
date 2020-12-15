package uk.ac.ucl.twitter.search.geo.file;

import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.Singleton;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Cache keeping a reference to JSON files collected. Configured as a
 * bean-managed singleton as explained in:
 * www.adam-bien.com/roller/abien/entry/singleton_the_perfect_cache_facade
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class FileReference {

  /**
   * Internal store of FileHandler instances. One instance per date per
   * location.
   */
  private final ConcurrentMap<String, FileHandler> fileHandlerMap =
    new ConcurrentHashMap<>();

  /**
   * Evaluates whether a FileHandler associated with a specific date and
   * location exists in the reference map.
   * @param dateAndLocation A String combining the date and location in the
   *                        format YYYY-MM-DD_Location
   * @return true if present, false otherwise
   */
  public boolean hasFile(final String dateAndLocation) {
    return fileHandlerMap.containsKey(dateAndLocation);
  }

  /**
   * Adds a new FileHandler instance to the reference map.
   * @param dateAndLocation A String combining the date and location in the
   *                        format YYYY-MM-DD_Location
   * @param fileHandler     An instance of FIleHandler
   */
  public void addFileReference(
    final String dateAndLocation, final FileHandler fileHandler
  ) {
    fileHandlerMap.put(dateAndLocation, fileHandler);
  }

  /**
   * Removes a file reference from the collection.
   * @param dateAndLocation A String combining the date and location in the
   *                        format YYYY-MM-DD_Location
   */
  public void removeFileReference(final String dateAndLocation) {
    fileHandlerMap.remove(dateAndLocation);
  }

  /**
   * Compiles a list of all the paths to files in the reference map cache.
   * @return A list of all the paths in the cache
   */
  public List<Path> getAllPaths() {
    return fileHandlerMap
      .values()
      .stream()
      .map(FileHandler::getPath)
      .collect(Collectors.toList());
  }

  /**
   * Returns a reference to a file specified by its date and geo location.
   * @param dateAndLocation
   * @return a FileHandler reference to the file
   */
  public FileHandler get(final String dateAndLocation) {
    return fileHandlerMap.get(dateAndLocation);
  }

}
