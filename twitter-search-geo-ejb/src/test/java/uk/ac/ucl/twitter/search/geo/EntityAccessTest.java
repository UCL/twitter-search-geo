package uk.ac.ucl.twitter.search.geo;

import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class EntityAccessTest {

  @Tested
  private EntityAccess instance;

  @Injectable
  private EntityManager entityManager;

  /*
  Evaluates that the method calls the correct named query and passes the
  correct parameter.
   */
  @Test
  public void testFindByLocation(@Mocked TypedQuery<LocationEntity> mock) {
    instance.findLocationEntityByLocation(Location.Aberdeen);
    new Verifications() {{
      String parameter;
      Location parameterValue;
      String queryName;
      Class<LocationEntity> entityClass;
      entityManager.createNamedQuery(
        queryName = withCapture(), entityClass = withCapture()
      );
      mock.setParameter(
        parameter = withCapture(), parameterValue = withCapture()
      );
      Assertions.assertEquals(LocationEntity.QUERY_FIND_BY_LOCATION, queryName);
      Assertions.assertEquals(LocationEntity.class, entityClass);
      Assertions.assertEquals(LocationEntity.PARAM_LOCATION, parameter);
      Assertions.assertEquals(Location.Aberdeen, parameterValue);
    }};
  }

}
