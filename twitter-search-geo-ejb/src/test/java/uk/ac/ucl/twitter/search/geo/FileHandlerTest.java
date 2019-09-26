package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class FileHandlerTest {

  private final String jsonResponse =
    "{\"statuses\":[{\"name\":\"Status 1\"},{\"name\":\"Status 2\"}],\"search_metadata\":{\"max_id\":1125490788736032800,\"count\":2,\"since_id\":0}}";

  private final String jsonAddResponse =
    "{\"statuses\":[{\"name\":\"Status 3\"},{\"name\":\"Status 4\"}],\"search_metadata\":{\"max_id\":1125490788736032802,\"count\":2,\"since_id\":0}}";

  private final String fileName = LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "_" + Location.Aberdeen.name();

  @AfterEach
  public void removeTempFiles() throws IOException {
    FileHandler.createFileHandler(fileName).deleteFile();
  }

  @Test
  public void testWriteStatuses() throws IOException {
    FileHandler instance = FileHandler.createFileHandler(fileName);
    StatusData.Metadata metadata = instance.writeStatuses(jsonResponse);
    Assertions.assertEquals(2, metadata.getCount());
    Assertions.assertEquals(1125490788736032800L, metadata.getMaxId());
  }

  @Test
  public void testWriteStatusesAndAppend() throws IOException {
    FileHandler instance = FileHandler.createFileHandler(fileName);
    StatusData.Metadata metadata = instance.writeStatuses(jsonResponse);
    Assertions.assertEquals(2, metadata.getCount());
    Assertions.assertEquals(1125490788736032800L, metadata.getMaxId());
    StatusData.Metadata metadataAdd = instance.writeStatuses(jsonAddResponse);
    Assertions.assertEquals(2, metadataAdd.getCount());
    Assertions.assertEquals(1125490788736032802L, metadataAdd.getMaxId());
    Path path = Paths.get(System.getProperty("java.io.tmpdir"), fileName);
    int size = Files.readAllLines(path).size();
    Assertions.assertEquals(1, size);
    String content = new String(Files.readAllBytes(path));
    long count = content.chars().filter(c -> c == ',').count();
    Assertions.assertEquals(3, count);
  }

  @Test
  public void testCloseFile() throws IllegalAccessException, IOException {
    FileHandler instance = FileHandler.createFileHandler(fileName);
    instance.writeStatuses(jsonResponse);
    instance.writeStatuses(jsonAddResponse);
    instance.closeFile();
    Field[] fields = FileHandler.class.getDeclaredFields();
    String className = StandardOpenOption.class.getName();
    Field internal = Arrays
      .stream(fields)
      .filter(f -> f.getType().getName().equals(className))
      .findFirst()
      .get();
    internal.setAccessible(true);
    StandardOpenOption openOption = (StandardOpenOption) internal.get(instance);
    Assertions.assertEquals(StandardOpenOption.READ, openOption);
    byte[] fileBytes = Files.readAllBytes(Paths.get(System.getProperty("java.io.tmpdir"), fileName));
    String closedFileContents = new String(fileBytes);
    Assertions.assertTrue(closedFileContents.startsWith("["));
    Assertions.assertTrue(closedFileContents.endsWith("]"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testOneFileHandlerPerDateAndLocation() throws IllegalAccessException {
    Field[] fields = FileHandler.class.getDeclaredFields();
    final String internalType = "java.util.Map<java.lang.String, uk.ac.ucl.twitter.search.geo.FileHandler>";
    Field internalMap = Arrays
      .stream(fields)
      .filter(f -> f.getGenericType().getTypeName().equals(internalType))
      .findFirst().get();
    internalMap.setAccessible(true);
    FileHandler.createFileHandler(fileName);
    FileHandler.createFileHandler(fileName + "2");
    final Object o = internalMap.get(FileHandler.createFileHandler(fileName));
    java.util.Map<java.lang.String, uk.ac.ucl.twitter.search.geo.FileHandler> m = (java.util.Map<java.lang.String, uk.ac.ucl.twitter.search.geo.FileHandler>) o;
    Assertions.assertEquals(2, m.size());
  }

}
