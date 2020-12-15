package uk.ac.ucl.twitter.search.geo;

import uk.ac.ucl.twitter.search.geo.client.ClientConfiguration;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
// import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * Encrypts the collected JSON files for archiving.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 * @since 1.0
 */
public class FileEncryption {

  public static final String CREDENTIAL_NAME = "LZO_ENCRYPTION_CREDENTIAL";

  private static final byte[] ENCRYPTION_KEY = ClientConfiguration
    .getPropertyFromSystem(CREDENTIAL_NAME);

  private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

  private final Cipher cipher;

  private static final SecureRandom secureRandom = new SecureRandom();

  private final SecretKeyFactory factory = SecretKeyFactory
    .getInstance("PBKDF2WithHmacSHA1");

  public FileEncryption() throws NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
    byte[] salt = new byte[16];
    secureRandom.nextBytes(salt);
    final KeySpec keySpec = new PBEKeySpec(
      bytesToChars(ENCRYPTION_KEY), salt, 65536, 128
    );

    cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(
      Cipher.ENCRYPT_MODE,
      new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES")
    );
  }

  public void encrypt(final Path path) {
    //Files.write()
  }

  private char[] bytesToChars(byte[] bytes) {
    Charset charset = StandardCharsets.UTF_8;
    CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(bytes));
    return Arrays.copyOf(charBuffer.array(), charBuffer.limit());
  }

}
