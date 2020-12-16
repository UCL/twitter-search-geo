package uk.ac.ucl.twitter.search.geo.persistence;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
@LocalBean
public class EntityAccess {

  /**
   * JPA entity manager.
   */
  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Retrieves counts and last id for geo location.
   * @param location the geo location
   * @return the JPA entity with the counts and last id collected
   */
  public LocationEntity findLocationEntityByLocation(final Location location) {
    final TypedQuery<LocationEntity> typedQuery = entityManager
      .createNamedQuery(
        LocationEntity.QUERY_FIND_BY_LOCATION,
        LocationEntity.class
      );
    typedQuery.setParameter(LocationEntity.PARAM_LOCATION, location);
    return typedQuery.getSingleResult();
  }

  /**
   * Retrieves the schedule information for each geo location.
   * @return the schedule information
   */
  public List<ScheduleAppLocationEntity> findAllScheduleAppLocationEntity() {
    final TypedQuery<ScheduleAppLocationEntity> typedQuery = entityManager
      .createNamedQuery(
        ScheduleAppLocationEntity.QUERY_FIND_ALL,
        ScheduleAppLocationEntity.class
      );
    return typedQuery.getResultList();
  }

  /**
   * Updates the counts and last id per location.
   * @param locationEntity JPA containing the last count ahd id retrieved
   */
  public void updateLocationEntity(final LocationEntity locationEntity) {
    entityManager.merge(locationEntity);
  }
}
