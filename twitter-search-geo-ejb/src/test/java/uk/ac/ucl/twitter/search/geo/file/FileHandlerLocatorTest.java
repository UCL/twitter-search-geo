package uk.ac.ucl.twitter.search.geo.file;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FileHandlerLocatorTest {

  @Mock
  private FileReference fileReference;

  @Mock
  private FileHandler fileHandler;

  @InjectMocks
  private FileHandlerLocator instance;

  @Test
  public void testClose() throws IOException {
    List<Path> allPaths = new ArrayList<>();
    allPaths.add(
      Paths.get(System.getProperty("java.io.tmpdir"), "2021-01-06_Location1")
    );
    allPaths.add(
      Paths.get(System.getProperty("java.io.tmpdir"), "2021-01-06_Location2")
    );

    when(fileReference.getAllPaths()).thenReturn(allPaths);

    when(fileHandler.getPath()).thenReturn(allPaths.get(0), allPaths.get(1));

    when(fileReference.get(anyString())).thenReturn(fileHandler);

    instance.close("2021-01-07");

    verify(fileHandler, times(2)).closeFile();

  }

}
