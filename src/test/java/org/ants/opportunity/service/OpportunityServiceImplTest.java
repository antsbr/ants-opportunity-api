package org.ants.opportunity.service;

import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.repository.OpportunityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void testGetAllOpportunities() {
    }

    @Test
    void testGetOpportunitiesAround() {
    }

    @Test
    void testGetOpportunitiesWithName() {
    }

    @Test
    void testGetOpportunitiesWithNameLike() {
    }

    @Test
    void testRemoveOpportunity() {
    }

    @Test
    void testGetOpportunitiesCount() {
    }

    @Test
    void testGetOpportunitiesCountAround() {
    }

    @Test
    void testGetOpportunitiesWithType() {
    }
}