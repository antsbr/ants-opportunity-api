package org.ants.opportunity.service;

import org.ants.opportunity.model.Opportunity;
import org.bson.types.ObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface OpportunityServiceImplTest {

    Opportunity save(Opportunity opportunity);

    Optional<Opportunity> getOpportunity(ObjectId id);

    List<Opportunity> getAllOpportunities();

    List<Opportunity> getOpportunitiesAround(Point near, Distance maxDistance);

    List<Opportunity> getOpportunitiesWithName(String name);

    List<Opportunity> getOpportunitiesWithNameLike(String name);

    void removeOpportunity(Opportunity opportunity);

    Integer getOpportunitiesCount();

    Integer getOpportunitiesCountAround();

    List<Opportunity> getOpportunitiesWithType(String type);

}