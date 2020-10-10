package org.ants.opportunity.service;

import org.ants.opportunity.model.Opportunity;
import org.bson.types.ObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface OpportunityService {

    Opportunity save(Opportunity opportunity);

    Optional<Opportunity> getOpportunity(ObjectId id);

    List<Opportunity> getAllOpportunities();

    List<Opportunity> getOpportunitiesAround(Point near, Distance maxDistance);

    List<Opportunity> getOpportunitiesWithName(String name);

    void removeOpportunity(Opportunity opportunity);

    Integer getOpportunitiesCount();

    Integer getOpportunitiesCountAround(Point near, Distance maxDistance);

    List<Opportunity> getOpportunitiesWithType(String type);

}