package uk.ac.ucl.twitter.search.geo;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class LocationEntityAccess {

  @PersistenceContext
  private EntityManager entityManager;

  public LocationEntity findByLocation(final Location location) {
    final TypedQuery<LocationEntity> typedQuery = entityManager
      .createNamedQuery(
        LocationEntity.QUERY_FIND_BY_LOCATION,
        LocationEntity.class
      );
    typedQuery.setParameter(LocationEntity.PARAM_LOCATION, location);
    return typedQuery.getSingleResult();
  }
}
