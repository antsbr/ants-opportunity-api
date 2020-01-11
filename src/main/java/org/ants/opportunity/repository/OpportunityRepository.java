package org.ants.opportunity.repository;

import org.ants.opportunity.model.Opportunity;
import org.bson.types.ObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OpportunityRepository extends MongoRepository<Opportunity, ObjectId> {

    List<Opportunity> findByType(String type);

    List<Opportunity> findByName(String name);

    List<Opportunity> findAll();

    List<Opportunity> findByNameLike(String name);

    Optional<Opportunity> findById(ObjectId id);

    void delete(Opportunity opportunity);

    List<Opportunity> findByLocationNear(Point near, Distance maxDistance);

    Opportunity save(Opportunity opportunity);
}
