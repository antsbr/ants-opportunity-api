package org.ants.opportunity.event;

import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.ants.opportunity.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Type.class)
public class TypeEventHandler {
    private final OpportunityRepository opportunities;

    @Autowired
    public TypeEventHandler(OpportunityRepository opportunities){
        this.opportunities = opportunities;
    }

    @HandleAfterSave
    public void updateOpportunityInfo(Type type) {
        for ( Opportunity opportunity : opportunities.findByTypeId(type.getId()) ){
            opportunity.setType(type);
            opportunities.save(opportunity);
        }
    }
}
