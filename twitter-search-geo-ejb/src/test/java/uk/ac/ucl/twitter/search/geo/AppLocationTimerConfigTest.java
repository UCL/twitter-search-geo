package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AppLocationTimerConfigTest {

  @Test
  public void testSerialization() throws IOException, ClassNotFoundException {
    final String fileName =  "appLocationTimerConfig";
    File file = File.createTempFile(fileName, ".tmp");
    AppLocationTimerConfig instance = new AppLocationTimerConfig(
      Location.Greater_London,
      "applicationName"
    );
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
      fileOutputStream
    );
    objectOutputStream.writeObject(instance);
    objectOutputStream.flush();
    objectOutputStream.close();
    FileInputStream fileInputStream = new FileInputStream(file);
    ObjectInputStream objectInputStream = new ObjectInputStream(
      fileInputStream
    );
    AppLocationTimerConfig result = (AppLocationTimerConfig) objectInputStream
      .readObject();
    objectInputStream.close();
    Assertions.assertEquals(Location.Greater_London, result.getLocation());
    Assertions.assertEquals("applicationName", result.getApplicationName());
  }

}
