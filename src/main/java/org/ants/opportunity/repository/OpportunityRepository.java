package org.ants.opportunity.repository;

import org.ants.opportunity.model.Opportunity;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "Opportunity", path = "opportunity")
public interface OpportunityRepository extends MongoRepository<Opportunity, String> {

    @Query("{'type.name': ?0}")
    List<Opportunity> findByType(@Param("type") String type);

    List<Opportunity> findByLocationNear(@Param("latitude-longitude") Point near, @Param("maxdistance") Distance maxDistance);

}
