package org.ants.opportunity.event;

import org.ants.opportunity.exception.OpportunityTypeNotFoundException;
import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.ants.opportunity.repository.OpportunityRepository;
import org.ants.opportunity.repository.TypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.expression.spel.ast.OpOr;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Opportunity.class)
public class OpportunityEventHandler {
    private final TypeRepository types;
    private final OpportunityRepository opportunities;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public OpportunityEventHandler(TypeRepository types, OpportunityRepository opportunities){
        this.opportunities = opportunities;
        this.types = types;
    }

    @HandleBeforeCreate
    public void logBeforeCreation(Opportunity opportunity){
        logger.debug("Creating opportunity: "+opportunity.toString());
    }

    @HandleAfterCreate
    public void logAfterCreation(Opportunity opportunity){
        logger.debug("Opportunity Created: "+opportunity.toString());
        logger.info("Opportunity Created: "+opportunity.toString());
    }

    @HandleBeforeSave
    public void logBeforeUpdate(Opportunity opportunity){
        Opportunity oldOpportunity = opportunities.findOne(opportunity.getId().toString());
        logger.debug("Updating opportunity from "+oldOpportunity+ " To " +opportunity.toString());
    }

    @HandleAfterSave
    public void logAfterUpdate(Opportunity opportunity){
        Opportunity oldOpportunity = opportunities.findOne(opportunity.getId().toString());
        logger.debug("Opportunity Updated from "+oldOpportunity+ " To " +opportunity.toString());
        logger.info("Opportunity Updated from "+oldOpportunity+ " To " +opportunity.toString());
    }

    @HandleBeforeDelete
    public void logBeforeDelete(Opportunity opportunity){
        logger.debug("Deleting opportunity: "+opportunity.toString());
    }

    @HandleBeforeDelete
    public void logAfterDelete(Opportunity opportunity){
        logger.debug("Opportunity Deleted: "+opportunity.toString());
        logger.info("Opportunity Deleted: "+opportunity.toString());
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    public void validateType(Opportunity opportunity) {
        Type type = opportunity.getType();
        type = types.findByName(type.getName());
        if( type != null ){
            opportunity.setType(type);
        }else {
            throw new OpportunityTypeNotFoundException
                    ("The type with name '"+ opportunity.getType().getName() +"' was not found.");
        }
    }

}
