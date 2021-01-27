package uk.ac.ucl.twitter.search.geo.file;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import static java.nio.file.Paths.get;

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
   * Password for encryption.
   */
   private static final char[] ENCRYPTION_PASS = System
    .getProperty(CREDENTIAL_NAME, "")
    .toCharArray();

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
  private final SecureRandom secureRandom = new SecureRandom();

  /**
   * Factory for password-based secret keys with a length of 512 bits.
   */
  private final SecretKeyFactory factory = SecretKeyFactory
    .getInstance("PBKDF2WithHmacSHA256");

  /**
   * Number of bytes to use in cryptographic salt.
   */
  private static final int SALT_BYTES = 8;

  /**
   * Size of buffer to read the input stream.
   */
  private static final int BUFFER_SIZE = 16 * 1024;

  /**
   * Constructs and configures a new instance of the encryptor.
   * @throws NoSuchPaddingException if transformation contains a padding scheme
   * that is not available
   * @throws NoSuchAlgorithmException if transformation is null, empty, in an
   * invalid format, or if no Provider supports a CipherSpi implementation for
   * the specified algorithm.
   */
  public FileEncryption() throws NoSuchPaddingException,
    NoSuchAlgorithmException {
    cipher = Cipher.getInstance(TRANSFORMATION);
  }

  /**
   * Encrypts a file specified by a path.
   * @param path path of the file to be encrypted
   * @return true if encrypted, false otherwise
   */
  public boolean encryptForOpenSSL111(final Path path) {
    final byte[] salt = generateSalt();
    // Generates a 32 byte key plus a 16 byte IV
    final KeySpec keySpec = new PBEKeySpec(
      ENCRYPTION_PASS, salt, 65536, 48 * 8
    );
    final byte[] encodedSecretAndIv;
    try {
      encodedSecretAndIv = factory.generateSecret(keySpec).getEncoded();
    } catch (InvalidKeySpecException e) {
      return false;
    }
    final byte[] encodedSecret = Arrays.copyOfRange(encodedSecretAndIv, 0, 32);
    final byte[] encodedIv = Arrays.copyOfRange(encodedSecretAndIv, 32, 48);
    SecretKey secretKey = new SecretKeySpec(encodedSecret, "AES");
    AlgorithmParameterSpec ivSpec = new IvParameterSpec(encodedIv);
    try {
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      return false;
    }
    Path encPath = get(path.toString().concat(".enc"));
    try (
      BufferedInputStream bis = new BufferedInputStream(
        Files.newInputStream(path, StandardOpenOption.READ), BUFFER_SIZE
      );
      BufferedOutputStream bos = new BufferedOutputStream(
        Files.newOutputStream(encPath, StandardOpenOption.CREATE), BUFFER_SIZE
      );
      CipherOutputStream cos = new CipherOutputStream(bos, cipher)
    ) {
      bos.write(prependSaltWithMagic(salt));
      int b;
      while ((b = bis.read()) != -1) {
        cos.write(b);
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  private byte[] generateSalt() {
    byte[] salt = new byte[SALT_BYTES];
    secureRandom.nextBytes(salt);
    return salt;
  }

  private byte[] prependSaltWithMagic(final byte[] salt) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] opensslMagic = "Salted__".getBytes(StandardCharsets.UTF_8);
    baos.write(opensslMagic);
    baos.write(salt);
    return baos.toByteArray();
  }

}
