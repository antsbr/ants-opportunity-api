package org.ants.opportunity.repository;

import org.ants.opportunity.model.Opportunity;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "Opportunity", path = "opportunity")
public interface OpportunityRepository extends MongoRepository<Opportunity, String> {

    @Cacheable({"opportunity"})
    @Query("{'type.name': ?0}")
    List<Opportunity> findByType(@Param("type") String type);

    @Cacheable({"opportunity"})
    @Query("{'type.id': ?0}")
    List<Opportunity> findByTypeId(@Param("typeId") ObjectId typeId);

    @Cacheable({"opportunity"})
    List<Opportunity> findAll();

    @Cacheable({"opportunity"})
    Opportunity findById(ObjectId id);

    @CachePut({"opportunity"})
    Opportunity save(Opportunity opportunity);

    @CacheEvict({"opportunity"})
    void delete(Opportunity opportunity);

    @Cacheable({"opportunity"})
    List<Opportunity> findByLocationNear(@Param("latitude-longitude") Point near, @Param("maxdistance") Distance maxDistance);
}
