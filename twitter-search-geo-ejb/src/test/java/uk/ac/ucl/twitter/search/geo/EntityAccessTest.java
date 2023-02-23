package uk.ac.ucl.twitter.search.geo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import uk.ac.ucl.twitter.search.geo.persistence.EntityAccess;
import uk.ac.ucl.twitter.search.geo.persistence.Location;
import uk.ac.ucl.twitter.search.geo.persistence.LocationEntity;

@ExtendWith(MockitoExtension.class)
public class EntityAccessTest {

  @Mock
  private EntityManager entityManager;

  @InjectMocks
  private EntityAccess instance;

  /*
  Evaluates that the method calls the correct named query and passes the
  correct parameter.
   */
  @Test
  public void testFindByLocation(@Mock TypedQuery<LocationEntity> mock) {
    when(
        entityManager.createNamedQuery(
            LocationEntity.QUERY_FIND_BY_LOCATION,
            LocationEntity.class))
    .thenReturn(mock);

    instance.findLocationEntityByLocation(Location.Aberdeen);

    verify(entityManager, times(1))
        .createNamedQuery(
            LocationEntity.QUERY_FIND_BY_LOCATION,
            LocationEntity.class);

    ArgumentCaptor<String> param0 = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Location> param1 = ArgumentCaptor.forClass(Location.class);

    verify(mock).setParameter(param0.capture(), param1.capture());

    Assertions.assertThat(param0.getValue())
        .isEqualTo(LocationEntity.PARAM_LOCATION);
    Assertions.assertThat(param1.getValue())
        .isEqualTo(Location.Aberdeen);

  }

}
