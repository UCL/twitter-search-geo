package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@see uk.ac.ucl.twitter.search.geo.LocationEntity}
 * based on the recommendations listed in
 * <a href="http://mjremijan.blogspot.com/2016/06/unit-testing-jpa-annotationsstop_20.html">
 * http://mjremijan.blogspot.com/2016/06/unit-testing-jpa-annotationsstop_20.html
 * </a>
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class LocationEntityTest {

  @Test
  public void hasEntityAnnotation() {
    Class<LocationEntity> clEntity = LocationEntity.class;
    Entity annotation = clEntity.getAnnotation(Entity.class);
    assertNotNull(annotation, "Expect LocationEntity to be annotated as Entity");
    assertEquals("", annotation.name(), "Expect name to be empty");
  }

  @Test
  public void hasRequiredFields() {
    Class<LocationEntity> clEntity = LocationEntity.class;
    assertAll("Expect LocationEntity to have all required fields",
      () -> assertNotNull(clEntity.getDeclaredField("location")),
      () -> assertNotNull(clEntity.getDeclaredField("count")),
      () -> assertNotNull(clEntity.getDeclaredField("sinceId"))
    );
  }

  @Test
  public void hasRequiredMethods() {
    Class<LocationEntity> clEntity = LocationEntity.class;
    assertAll("Expect LocationEntity to have all required methods",
      () -> assertNotNull(clEntity.getDeclaredMethod("getLocation")),
      () -> assertNotNull(clEntity.getDeclaredMethod("setLocation", Location.class)),
      () -> assertNotNull(clEntity.getDeclaredMethod("getSinceId")),
      () -> assertNotNull(clEntity.getDeclaredMethod("setSinceId", String.class)),
      () -> assertNotNull(clEntity.getDeclaredMethod("getCount")),
      () -> assertNotNull(clEntity.getDeclaredMethod("setCount", int.class))
    );
  }

  @Test
  public void testLocationField() throws NoSuchFieldException {
    Class<LocationEntity> clEntity = LocationEntity.class;
    Field field = clEntity.getDeclaredField("location");
    assertNotNull(field.getAnnotation(Enumerated.class));
  }
}
