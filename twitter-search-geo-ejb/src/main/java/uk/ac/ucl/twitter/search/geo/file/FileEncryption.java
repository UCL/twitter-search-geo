package uk.ac.ucl.twitter.search.geo.file;

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

  /**
   * Name of the system property to call the encryption credential.
   */
  public static final String CREDENTIAL_NAME = "LZO_ENCRYPTION_CREDENTIAL";

  /**
   * Key used for AES encryption.
   */
  private static final byte[] ENCRYPTION_KEY = ClientConfiguration
    .getPropertyFromSystem(CREDENTIAL_NAME);

  /**
   * Transformation definition for configuration of the cipher.
   */
  private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

  /**
   * Cryptographic cipher for encryption.
   */
  private final Cipher cipher;

  /**
   * Random number generator for cryptography.
   */
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  /**
   * Factory for password-based secret keys with hash length of 160 bits.
   */
  private final SecretKeyFactory factory = SecretKeyFactory
    .getInstance("PBKDF2WithHmacSHA1");

  /**
   * Number of bytes to use in cryptographic salt.
   */
  private static final int SALT_BYTES = 16;

  /**
   * Constructs and configures a new instance of the encryptor.
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   * @throws InvalidKeyException
   */
  public FileEncryption() throws NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
    byte[] salt = new byte[SALT_BYTES];
    SECURE_RANDOM.nextBytes(salt);
    final KeySpec keySpec = new PBEKeySpec(
      bytesToChars(ENCRYPTION_KEY), salt, 65536, 128
    );

    cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(
      Cipher.ENCRYPT_MODE,
      new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES")
    );
  }

  /**
   * Encrypts a file specified by a path.
   * @param path path of the file to be encrypted
   */
  public void encrypt(final Path path) {
    //Files.write()
  }

  private char[] bytesToChars(final byte[] bytes) {
    Charset charset = StandardCharsets.UTF_8;
    CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(bytes));
    return Arrays.copyOf(charBuffer.array(), charBuffer.limit());
  }

}
