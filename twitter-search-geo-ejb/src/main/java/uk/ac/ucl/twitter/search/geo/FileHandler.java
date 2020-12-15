package uk.ac.ucl.twitter.search.geo;


import jakarta.json.Json;
import jakarta.json.stream.JsonParser;
import uk.ac.ucl.twitter.search.geo.client.ClientConfiguration;
import uk.ac.ucl.twitter.search.geo.client.StatusData;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Provides access to JSON files where the data is persisted. Supports creation,
 * update and compression of files. There is expected to be one file per
 * day per location.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
public final class FileHandler {

  /**
   * Configuration key for SEARCH_GEO_DIR.
   */
  public static final String SEARCH_GEO_KEY = "SEARCH_GEO_DIR";

  /**
   * The directory where the JSON files are saved.
   */
  @Deprecated
  public static final String SEARCH_GEO_DIR = ClientConfiguration
    .getFromSystemOrEnvOrElse(
      SEARCH_GEO_KEY,
      System.getProperty("java.io.tmpdir")
    );

  /**
   * Searches system properties and environment properties for the name of the
   * directory where the JSON files are saved. It can be configured with the
   * key SEARCH_GEO_DIR.
   * @return The name of the directory where the JSON files are saved. Defaults
   * to java.io.tmpdir
   */
  public static String getSearchGeoDir() {
    return ClientConfiguration
      .getFromSystemOrEnvOrElse(
        SEARCH_GEO_KEY,
        System.getProperty("java.io.tmpdir"));
  }

  /**
   * The Path to the JSON file. File name xpected to be a string in the format
   * YYYY-MM-DD_Location.
   */
  private final Path path;

  /**
   * Obtains the path to the file.
   * @return A Path object of the file
   */
  public Path getPath() {
    return path;
  }

  /**
   * Current state of the JSON file. Defaults to StandardOpenOption.CREATE
   */
  private StandardOpenOption openOption = StandardOpenOption.CREATE;

  /**
   * Left square bracket character.
   */
  private static final byte[] LEFT_SQUARE_BRACKET = "["
    .getBytes(StandardCharsets.UTF_8);

  /**
   * Right square bracket character.
   */
  private static final byte[] RIGHT_SQUARE_BRACKET = "]"
    .getBytes(StandardCharsets.UTF_8);

  FileHandler(final String jsonFile) {
    path = Paths.get(SEARCH_GEO_DIR, jsonFile);
  }

  /**
   * Writes a collection of tweets (status element) in a file.
   * @param jsonResponse The response returned by the standard search API in
   *                     JSON format
   * @return Metadata to use for pagination in subsequent queries and monitoring
   * @throws IOException If JSON file cannot be written
   */
  public StatusData.Metadata writeStatuses(final String jsonResponse)
    throws IOException {
    if (openOption.equals(StandardOpenOption.READ)) {
      throw new IOException("File has been closed and set as read-only.");
    }
    final StatusData statusData = extractStatus(jsonResponse);
    Files.write(
      path,
      statusData.getStatuses().getBytes(StandardCharsets.UTF_8),
      openOption
    );
    openOption = StandardOpenOption.APPEND;
    return statusData.getMetaData();
  }

  /**
   * Closes the JSON file to prevent new content to be appended.
   * @throws IOException if the file cannot be read of written
   */
  public void closeFile() throws IOException {
    byte[] jsonByteContent = Files.readAllBytes(path);
    final int combinedLength = LEFT_SQUARE_BRACKET.length
      + jsonByteContent.length + LEFT_SQUARE_BRACKET.length;
    byte[] combinedByteContent = Arrays.copyOf(
      LEFT_SQUARE_BRACKET, combinedLength
    );
    int offset = LEFT_SQUARE_BRACKET.length;
    System.arraycopy(
      jsonByteContent, 0, combinedByteContent, offset, jsonByteContent.length
    );
    offset += jsonByteContent.length;
    System.arraycopy(
      RIGHT_SQUARE_BRACKET, 0, combinedByteContent, offset,
      RIGHT_SQUARE_BRACKET.length
    );
    Files.write(path, combinedByteContent, StandardOpenOption.WRITE);
    openOption = StandardOpenOption.READ;
  }

  /**
   * Deletes a JSON file from the filesystem. It does not remove the reference
   * from the cache which has to be removed manually.
   * @return The dateAndLocation reference to the file
   * @throws IOException If an I/O error occurs
   */
  public String deleteFile() throws IOException {
    Files.deleteIfExists(path);
    return path.getFileName().toString();
  }

  private StatusData extractStatus(final String jsonResponse) {
    final JsonParser jsonParser = Json.createParser(
      new StringReader(jsonResponse)
    );
    final StatusData statusData = new StatusData();
    while (jsonParser.hasNext()) {
      final JsonParser.Event event = jsonParser.next();
      boolean isKeyName = event.equals(JsonParser.Event.KEY_NAME);
      if (isKeyName && jsonParser.getString().equals("statuses")) {
        jsonParser.next();
        final String comma = openOption
          .equals(StandardOpenOption.APPEND) ? "," : "";
        final String status = comma + jsonParser
          .getArray()
          .toString()
          .trim()
          .replaceAll("^\\[|\\]$", "");
        statusData.setStatuses(status);
      } else if (isKeyName && jsonParser.getString().equals("max_id")) {
        jsonParser.next();
        statusData.getMetaData().setMaxId(jsonParser.getLong());
      } else if (isKeyName && jsonParser.getString().equals("count")) {
        jsonParser.next();
        statusData.getMetaData().setCount(jsonParser.getInt());
      }
    }
    return statusData;
  }

}
