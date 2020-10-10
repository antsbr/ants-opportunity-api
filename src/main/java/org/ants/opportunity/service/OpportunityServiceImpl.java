package org.ants.opportunity.service;

import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.OpportunityTypeEnum;
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
        return opportunityRepository.findById(id);
    }

    @Override
    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    @Override
    public List<Opportunity> getOpportunitiesAround(Point near, Distance maxDistance) {
        return opportunityRepository.findByLocationNear(near, maxDistance);
    }

    @Override
    public List<Opportunity> getOpportunitiesWithName(String name) {
        return opportunityRepository.findByNameLike(name);
    }

    @Override
    public void removeOpportunity(Opportunity opportunity) {
        opportunityRepository.delete(opportunity);
    }

    @Override
    public Integer getOpportunitiesCount() {
        long longNumberOfOpportunities = opportunityRepository.count();
        return Math.toIntExact(longNumberOfOpportunities);
    }

    @Override
    public Integer getOpportunitiesCountAround(Point near, Distance maxDistance) {
        long longNumberOfOpportunities = opportunityRepository.countOpportunitiesByLocationNear(near, maxDistance);
        return Math.toIntExact(longNumberOfOpportunities);
    }

    @Override
    public List<Opportunity> getOpportunitiesWithType(String type) {
        return opportunityRepository.findByType(OpportunityTypeEnum.valueOf(type.toUpperCase()));
    }
}
