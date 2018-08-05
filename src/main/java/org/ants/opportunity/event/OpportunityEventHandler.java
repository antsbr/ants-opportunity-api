package org.ants.opportunity.event;

import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.ants.opportunity.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

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
        Type type = opportunity.type;

        type = types.findByName(type.name);

        if( type != null ){
            opportunity.setType(type);
        }else {
            throw new NoSuchElementException
                    ("The type with name: "+ opportunity.type.name +" was not found.");
        }

    }

}
