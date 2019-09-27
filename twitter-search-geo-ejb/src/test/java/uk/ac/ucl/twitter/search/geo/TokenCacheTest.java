package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import java.lang.reflect.Method;

public class TokenCacheTest {

  private static final Class<TokenCache> tokenCacheClass = TokenCache.class;

  @Test
  public void hasSingletonAnnotation() {
    Singleton annotation = tokenCacheClass.getAnnotation(Singleton.class);
    Assertions.assertNotNull(annotation);
  }

  @Test
  public void hasLocktypeWriteOnSetBearer() throws NoSuchMethodException {
    Method method = tokenCacheClass.getDeclaredMethod(
      "setBearer", String.class, String.class
    );
    Lock annotation = method.getAnnotation(Lock.class);
    Assertions.assertNotNull(annotation);
    Assertions.assertEquals(LockType.WRITE, annotation.value());
  }

  @Test
  public void testReadDefaults() {
    TokenCache instance = new TokenCache();
    Assertions.assertFalse(instance.existsInCache("applicationName"));
    Assertions.assertEquals("", instance.getBearer("applicationName"));
  }

  @Test
  public void testReadAfterWrite() {
    TokenCache instance = new TokenCache();
    instance.setBearer("applicationName", "newBearer");
    Assertions.assertTrue(instance.existsInCache("applicationName"));
    Assertions.assertEquals("newBearer", instance.getBearer("applicationName"));
  }

}
