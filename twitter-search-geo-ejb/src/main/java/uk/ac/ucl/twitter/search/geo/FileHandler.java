package uk.ac.ucl.twitter.search.geo;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides access to JSON files where the data is persisted. Supports creation,
 * update and compression of files. There is expected to be one file per
 * location.
 */
public final class FileHandler {

  private static Map<Location, FileHandler> fileHandlerMap = new HashMap<>();

  private FileHandler() { }

  public static FileHandler createFileHandler(final Location location) {
    if (fileHandlerMap.containsKey(location)) {
      return fileHandlerMap.get(location);
    } else {
      return fileHandlerMap.put(location, new FileHandler());
    }
  }

  public long writeStatuses(final String jsonResponse) {
    return 1L;
  }

}
