package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@see uk.ac.ucl.twitter.search.geo.ScheduleAppLocationEntity}
 * based on the recommendations listed in
 * <a href="http://mjremijan.blogspot.com/2016/06/unit-testing-jpa-annotationsstop_20.html">
 * http://mjremijan.blogspot.com/2016/06/unit-testing-jpa-annotationsstop_20.html
 * </a>
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class ScheduleAppLocationEntityTest {

  @Test
  public void hasEntityAnnotation() {
    Class<ScheduleAppLocationEntity> clEntity = ScheduleAppLocationEntity.class;
    Entity annotation = clEntity.getAnnotation(Entity.class);
    assertNotNull(annotation, "Expect ScheduleAppLocationEntity to be annotated as Entity");
    assertEquals("", annotation.name(), "Expect name to be empty");
  }

  @Test
  public void hasRequiredFields() {
    Class<ScheduleAppLocationEntity> clEntity = ScheduleAppLocationEntity.class;
    assertAll("Expect ScheduleAppLocationEntity to have all required fields",
      () -> assertNotNull(clEntity.getDeclaredField("appName")),
      () -> assertNotNull(clEntity.getDeclaredField("intervalMinutes")),
      () -> assertNotNull(clEntity.getDeclaredField("location"))
    );
  }

  @Test
  public void hasRequiredMethods() {
    Class<ScheduleAppLocationEntity> clEntity = ScheduleAppLocationEntity.class;
    assertAll("Expect ScheduleAppLocationEntity to have all required methods",
      () -> assertNotNull(clEntity.getDeclaredMethod("getLocation")),
      () -> assertNotNull(clEntity.getDeclaredMethod("getIntervalMinutes")),
      () -> assertNotNull(clEntity.getDeclaredMethod("getAppName"))
    );
  }

  @Test
  public void testLocationField() throws NoSuchFieldException {
    Class<ScheduleAppLocationEntity> clEntity = ScheduleAppLocationEntity.class;
    Field field = clEntity.getDeclaredField("location");
    assertNotNull(field.getAnnotation(Enumerated.class));
  }

}
