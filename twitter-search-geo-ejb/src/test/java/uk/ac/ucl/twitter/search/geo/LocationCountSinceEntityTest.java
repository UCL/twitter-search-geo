package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@see uk.ac.ucl.twitter.search.geo.LocationCountSinceEntity}
 * based on the recommendations listed in
 * <a href="http://mjremijan.blogspot.com/2016/06/unit-testing-jpa-annotationsstop_20.html">
 * http://mjremijan.blogspot.com/2016/06/unit-testing-jpa-annotationsstop_20.html
 * </a>
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class LocationCountSinceEntityTest {

  @Test
  public void hasEntityAnnotation() {
    Class<LocationCountSinceEntity> clEntity = LocationCountSinceEntity.class;
    Entity annotation = clEntity.getAnnotation(Entity.class);
    assertNotNull(annotation, "Expect LocationCountSinceEntity to be annotated as Entity");
    assertEquals("", annotation.name(), "Expect name to be empty");
  }

  @Test
  public void hasRequiredFields() {
    Class<LocationCountSinceEntity> clEntity = LocationCountSinceEntity.class;
    assertAll("Expect LocationCountSinceEntity to have all required fields",
      () -> assertNotNull(clEntity.getDeclaredField("location")),
      () -> assertNotNull(clEntity.getDeclaredField("count")),
      () -> assertNotNull(clEntity.getDeclaredField("sinceId"))
    );
  }

  @Test
  public void hasRequiredMethods() {
    Class<LocationCountSinceEntity> clEntity = LocationCountSinceEntity.class;
    assertAll("Expect LocationCountSinceEntity to have all required methods",
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
    Class<LocationCountSinceEntity> clEntity = LocationCountSinceEntity.class;
    Field field = clEntity.getDeclaredField("location");
    assertNotNull(field.getAnnotation(Enumerated.class));
  }
}
