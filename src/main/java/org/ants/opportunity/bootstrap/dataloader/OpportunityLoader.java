package org.ants.opportunity.bootstrap.dataloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.OpportunityTypeEnum;
import org.ants.opportunity.repository.OpportunityRepository;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

@Component
public class OpportunityLoader {

    private OpportunityRepository opportunityRepository;
    private static final Faker FAKER = new Faker();

    public OpportunityLoader(OpportunityRepository opportunityRepository) {
         this.opportunityRepository = opportunityRepository;
    }

    public void addFakeElement(int numberOfOpportunities){
        for(int i = 0;i<numberOfOpportunities; i++) {
            opportunityRepository.save(getFakeElement());
        }
    }

    public Opportunity addElement(Opportunity opportunity){
        opportunityRepository.save(opportunity);
        return opportunity;
    }

    public static Opportunity getFakeElement(){
        return createFakeElement();
    }

    public static List<Opportunity> getListOfFakeElement(final Integer quantity){
        List<Opportunity> fakeOpportunities = new ArrayList<Opportunity>();
        for (int i=0; i < quantity; i++) {
            fakeOpportunities.add(createFakeElement());
        }
        return fakeOpportunities;
    }

    public void removeElement(Opportunity opportunity){
        opportunityRepository.delete(opportunity);
    }

    public void removeAllElements(){
        opportunityRepository.deleteAll();
    }

    public static OpportunityTypeEnum getRandomType(){
        Random r = new Random();
        OpportunityTypeEnum[] opportunityTypes = OpportunityTypeEnum.values();
		return opportunityTypes[r.nextInt(opportunityTypes.length)];
    }

    private static Opportunity createFakeElement(){

        // Creates the opportunity
        return Opportunity.builder().
        name(FAKER.beer().name()).
        type(getRandomType()).
        location(new GeoJsonPoint(Double.parseDouble(FAKER.address().longitude()),
        Double.parseDouble(FAKER.address().latitude()))).
        build();
    }
}