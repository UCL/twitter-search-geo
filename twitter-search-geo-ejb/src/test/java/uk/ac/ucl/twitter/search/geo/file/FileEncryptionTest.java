package uk.ac.ucl.twitter.search.geo.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FileEncryptionTest {

  private static final Path path = Paths.get(
    System.getProperty("java.io.tmpdir"), "testEncrypt"
  );

  private final Path pathEnc = Paths.get(
    System.getProperty("java.io.tmpdir"), "testEncrypt.enc"
  );

  @BeforeAll
  public static void beforeAll() throws IOException {
    String credential = "PASSWORD";
    System.setProperty(FileEncryption.CREDENTIAL_NAME, credential);
    final String testContent = "[{\"status\": \"Content\"}]";
    Files.write(
      path,
      testContent.getBytes(StandardCharsets.UTF_8),
      StandardOpenOption.CREATE
    );
  }

  @AfterAll
  public static void afterAll() throws IOException {
    System.clearProperty(FileEncryption.CREDENTIAL_NAME);
    Files.deleteIfExists(path);
  }

  @AfterEach
  public void afterEach() throws IOException {
    Files.deleteIfExists(pathEnc);
  }

  @Test
  @DisplayName("encryptForOpenSSL111 returns true if no exceptions are thrown")
  public void testencryptForOpenSSL111ReturnsTrue() throws NoSuchAlgorithmException, NoSuchPaddingException {
    FileEncryption instance = new FileEncryption();
    final boolean isEncrypted = instance.encryptForOpenSSL111(path);
    Assertions.assertTrue(isEncrypted);
  }

  @Test
  @DisplayName("encryptForOpenSSL111 must include salt magic")
  public void testencryptForOpenSSL111SaltMagic() throws NoSuchAlgorithmException, NoSuchPaddingException, IOException {
    FileEncryption instance = new FileEncryption();
    instance.encryptForOpenSSL111(path);
    byte[] encryptedBytes = Files.readAllBytes(pathEnc);
    byte[] actual = Arrays.copyOfRange(encryptedBytes, 0, 8);
    byte[] expected = "Salted__".getBytes(StandardCharsets.UTF_8);
    Assertions.assertArrayEquals(expected, actual);
  }
}
