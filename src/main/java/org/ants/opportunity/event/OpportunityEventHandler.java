package org.ants.opportunity.event;

import org.ants.opportunity.exception.OpportunityTypeNotFoundException;
import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.ants.opportunity.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Opportunity.class)
public class OpportunityEventHandler {
    private final TypeRepository types;
    @Autowired
    public OpportunityEventHandler(TypeRepository types){
        this.types = types;
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    public void validateType(Opportunity opportunity) {
        try{
            Type type = opportunity.getType();
            type = types.findByName(type.getName());
            if( type != null ){
                opportunity.setType(type);
            }else {
                throw new OpportunityTypeNotFoundException
                        ("The type with name '"+ opportunity.getType().getName() +"' was not found.");
            }
        }catch (NullPointerException exception){
            throw new OpportunityTypeNotFoundException
                    ("The type attribute has not been found.");
        }catch (OpportunityTypeNotFoundException exception){
            throw new OpportunityTypeNotFoundException
                    (exception.getMessage());
        }

    }

}
