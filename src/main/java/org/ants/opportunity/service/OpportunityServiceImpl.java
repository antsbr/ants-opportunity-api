package org.ants.opportunity.service;

import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.repository.OpportunityRepository;
import org.bson.types.ObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public class OpportunityServiceImpl implements OpportunityService{
    private final OpportunityRepository opportunityRepository;
    public OpportunityServiceImpl(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    @Override
    public Opportunity save(Opportunity opportunity) {
        return opportunityRepository.save(opportunity);
    }

    @Override
    public Optional<Opportunity> getOpportunity(ObjectId id) {
        return Optional.empty();
    }

    @Override
    public List<Opportunity> getAllOpportunities() {
        return null;
    }

    @Override
    public List<Opportunity> getOpportunitiesAround(Point near, Distance maxDistance) {
        return null;
    }

    @Override
    public List<Opportunity> getOpportunitiesWithName(String name) {
        return null;
    }

    @Override
    public List<Opportunity> getOpportunitiesWithNameLike(String name) {
        return null;
    }

    @Override
    public void removeOpportunity(Opportunity opportunity) {

    }

    @Override
    public Integer getOpportunitiesCount() {
        return null;
    }

    @Override
    public Integer getOpportunitiesCountAround() {
        return null;
    }

    @Override
    public List<Opportunity> getOpportunitiesWithType(String type) {
        return null;
    }
}
