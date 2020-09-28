package org.ants.opportunity.repository;

import org.ants.opportunity.bootstrap.dataloader.OpportunityLoader;
import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.OpportunityTypeEnum;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class OpportunityRepositoryTest {

    @Autowired
    OpportunityRepository opportunityRepository;

    @After
    public void cleanUp() {
        opportunityRepository.deleteAll();
    }

    @Test
    public void save() {
        final Opportunity opportunity = OpportunityLoader.getFakeElement();
        final Opportunity savedOpportunity = opportunityRepository.save(opportunity);
        assertEquals(opportunity, savedOpportunity);
    }

    @Test
    public void findByName() {
        final Opportunity firstOpportunity = OpportunityLoader.getFakeElement();
        firstOpportunity.setName("Twins");
        final Opportunity secondOpportunity = OpportunityLoader.getFakeElement();
        secondOpportunity.setName("Twins");
        opportunityRepository.saveAll(List.of(firstOpportunity,secondOpportunity));
        final List<Opportunity> opportunitiesFound = opportunityRepository.findByName("Twins");
        assertThat(opportunitiesFound, hasSize(2));
    }

    @Test
    public void findByNameLike() {
        final Opportunity fullNameOpportunity = OpportunityLoader.getFakeElement();
        fullNameOpportunity.setName("First Second Third");
        final Opportunity partialNameOpportunity = fullNameOpportunity;
        partialNameOpportunity.setName("Second Third");
        final Opportunity lastNameOpportunity = fullNameOpportunity;
        lastNameOpportunity.setName("Third");
        opportunityRepository.saveAll(List.of(fullNameOpportunity, partialNameOpportunity, lastNameOpportunity));
        final List<Opportunity> opportunitiesFound = opportunityRepository.findByNameLike("Third");
        assertThat(opportunitiesFound, hasSize(3));
    }

    @Test
    public void findByLocationNear() {
        final Opportunity ifspOpportunity = OpportunityLoader.getFakeElement();
        ifspOpportunity.setName("Instituto Federal de São Paulo - Quermece");
        ifspOpportunity.setLocation(-47.2332798, -22.8511083);
        final Opportunity cityCenterOpportunity = OpportunityLoader.getFakeElement();
        cityCenterOpportunity.setName("City center - Quermece");
        cityCenterOpportunity.setLocation(-47.212767116725445, -22.8608663540715);
        opportunityRepository.saveAll(List.of(ifspOpportunity, cityCenterOpportunity));

        Distance oneKm = new Distance(1, Metrics.KILOMETERS);
        Point ifspBuildingLocation = new Point(-47.2325119, -22.8503613);

        List<Opportunity> opportunitiesAround = opportunityRepository.findByLocationNear(ifspBuildingLocation, oneKm);
        assertThat(opportunitiesAround, hasSize(1));
        assertEquals(opportunitiesAround.get(0).name, "Instituto Federal de São Paulo - Quermece");
    }

    @Test
    public void delete() {
        Opportunity opportunity = OpportunityLoader.getFakeElement();
        opportunity = opportunityRepository.save(opportunity);
        final ObjectId opportunityId = opportunity.getId();
        assertNotNull(opportunityId);

        opportunityRepository.delete(opportunity);
        assertEquals(opportunityRepository.findById(opportunityId), Optional.empty());
    }

    @Test
    public void findAll() {
        final Integer numberOfOpportunities = 10;
        opportunityRepository.saveAll(OpportunityLoader.getListOfFakeElement(numberOfOpportunities));
        assertThat(opportunityRepository.findAll(), hasSize(numberOfOpportunities));
    }

    @Test
    public void findById() {
        Opportunity opportunity = OpportunityLoader.getFakeElement();
        opportunity.setName("findbyid");
        opportunity = opportunityRepository.save(opportunity);
        final ObjectId opportunityId = opportunity.getId();
        assertNotNull(opportunityId);

        Optional<Opportunity> opportunityFound = opportunityRepository.findById(opportunityId);
        assertNotEquals(Optional.empty(), opportunityFound);
        assertEquals(opportunity, opportunityFound.get());
    }

    @Test
    public void findByType() {
        Opportunity animal1Opportunity = OpportunityLoader.getFakeElement();
        animal1Opportunity.setType(OpportunityTypeEnum.ANIMAL);
        Opportunity animal2Opportunity = OpportunityLoader.getFakeElement();
        animal2Opportunity.setType(OpportunityTypeEnum.ANIMAL);
        Opportunity kidsOpportunity = OpportunityLoader.getFakeElement();
        kidsOpportunity.setType(OpportunityTypeEnum.KIDS);

        opportunityRepository.saveAll(List.of(animal1Opportunity, animal2Opportunity, kidsOpportunity));

        assertThat(opportunityRepository.findByType(OpportunityTypeEnum.KIDS), hasSize(1));
    }


}