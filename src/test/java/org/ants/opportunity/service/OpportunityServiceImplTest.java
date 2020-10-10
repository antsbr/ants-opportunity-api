package org.ants.opportunity.service;

import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.OpportunityTypeEnum;
import org.ants.opportunity.repository.OpportunityRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpportunityServiceImplTest {
    @Mock
    OpportunityRepository opportunityRepository;
    @InjectMocks
    OpportunityServiceImpl opportunityService;

    @Test
    void testSave() {
        Opportunity opportunity = new Opportunity();
        when(opportunityRepository.save(any(Opportunity.class))).thenReturn(opportunity);

        Opportunity savedOpportunity = opportunityService.save(new Opportunity());

        verify(opportunityRepository).save(any(Opportunity.class));
        assertThat(savedOpportunity).isNotNull();
    }

    @Test
    void testGetOpportunity() {
        Opportunity opportunity = new Opportunity();
        when(opportunityRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(opportunity));

        Optional<Opportunity> foundByIdOpportunity = opportunityService.getOpportunity(new ObjectId());

        verify(opportunityRepository).findById(any(ObjectId.class));
        assertThat(opportunity).isNotNull();
    }

    @Test
    void testGetAllOpportunities() {
        List<Opportunity> opportunityList = List.of(new Opportunity(), new Opportunity());
        when(opportunityRepository.findAll()).thenReturn(opportunityList);

        List<Opportunity> foundOpportunityList = opportunityService.getAllOpportunities();

        verify(opportunityRepository).findAll();
        assertThat(foundOpportunityList).isNotNull();
        assertThat(foundOpportunityList).isInstanceOf(List.class);
        assertThat(foundOpportunityList).hasSize(2);
    }

    @Test
    void testGetOpportunitiesAround() {
        List<Opportunity> opportunityList = List.of(new Opportunity(), new Opportunity());
        when(opportunityRepository.findByLocationNear(any(Point.class), any(Distance.class))).thenReturn(opportunityList);

        List<Opportunity> foundOpportunityList = opportunityService.getOpportunitiesAround(new Point(-0, -0), new Distance(1, Metrics.KILOMETERS));

        verify(opportunityRepository).findByLocationNear(any(Point.class), any(Distance.class));
        assertThat(foundOpportunityList).isNotNull();
        assertThat(foundOpportunityList).isInstanceOf(List.class);
        assertThat(foundOpportunityList).hasSize(2);
    }

    @Test
    void testGetOpportunitiesWithName() {
        List<Opportunity> opportunityList = List.of(new Opportunity(), new Opportunity());
        when(opportunityRepository.findByNameLike(any(String.class))).thenReturn(opportunityList);

        List<Opportunity> foundOpportunityList = opportunityService.getOpportunitiesWithName("Test");

        verify(opportunityRepository).findByNameLike(any(String.class));
        assertThat(foundOpportunityList).isNotNull();
        assertThat(foundOpportunityList).isInstanceOf(List.class);
        assertThat(foundOpportunityList).hasSize(2);
    }

    @Test
    void testGetOpportunitiesWithType() {
        List<Opportunity> opportunityList = List.of(new Opportunity(), new Opportunity());
        when(opportunityRepository.findByType(any(OpportunityTypeEnum.class))).thenReturn(opportunityList);

        List<Opportunity> foundOpportunityList = opportunityService.getOpportunitiesWithType(OpportunityTypeEnum.ANIMAL.getName());

        verify(opportunityRepository).findByType(any(OpportunityTypeEnum.class));
        assertThat(foundOpportunityList).isNotNull();
        assertThat(foundOpportunityList).isInstanceOf(List.class);
        assertThat(foundOpportunityList).hasSize(2);
    }

    @Test
    void testRemoveOpportunity() {
        opportunityService.removeOpportunity(new Opportunity());
        verify(opportunityRepository).delete(any(Opportunity.class));
    }

    @Test
    void testGetOpportunitiesCount() {
        when(opportunityRepository.count()).thenReturn(10L);

        int numberOfOpportunities = opportunityService.getOpportunitiesCount();

        verify(opportunityRepository).count();
        assertThat(numberOfOpportunities).isEqualTo(10);
        assertThat(numberOfOpportunities).isInstanceOf(Integer.class);
    }

    @Test
    void testGetOpportunitiesCountAround() {
        Point point = new Point(-0, -0);
        Distance distance = new Distance(1, Metrics.KILOMETERS);
        when(opportunityRepository.countOpportunitiesByLocationNear(point, distance)).thenReturn(10L);

        int numberOfOpportunities = opportunityService.getOpportunitiesCountAround(point, distance);

        verify(opportunityRepository).countOpportunitiesByLocationNear(point, distance);
        assertThat(numberOfOpportunities).isEqualTo(10);
        assertThat(numberOfOpportunities).isInstanceOf(Integer.class);
    }
}