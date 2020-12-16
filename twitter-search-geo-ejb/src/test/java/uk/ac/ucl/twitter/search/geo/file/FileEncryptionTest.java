package uk.ac.ucl.twitter.search.geo.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.ucl.twitter.search.geo.file.FileEncryption;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class FileEncryptionTest {

  private final Path path = Paths.get(
    System.getProperty("java.io.tmpdir"), "testEncrypt"
  );

  private final Path pathEnc = Paths.get(
    System.getProperty("java.io.tmpdir"), "testEncrypt.enc"
  );

  @BeforeEach
  public void setup() throws NoSuchAlgorithmException {
    SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
    final String credential = new String(secretKey.getEncoded());
    System.setProperty(FileEncryption.CREDENTIAL_NAME, credential);
  }

  @AfterEach
  public void teardown() throws IOException {
    System.clearProperty(FileEncryption.CREDENTIAL_NAME);
    Files.deleteIfExists(path);
    Files.deleteIfExists(pathEnc);
  }

  @Test
  @DisplayName("Encrypt in Java, decrypt with openssl")
  public void testEncrypt() throws IOException, InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
    final String testContent = "[{\"status\": \"Content\"}]";
    Files.write(
      path,
      testContent.getBytes(StandardCharsets.UTF_8),
      StandardOpenOption.CREATE
    );
    FileEncryption instance = new FileEncryption();
    instance.encrypt(path);
  }

}
