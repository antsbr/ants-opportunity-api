package org.ants.opportunity.repository;

import org.ants.opportunity.model.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "Type", path = "type")
public interface TypeRepository extends MongoRepository<Type, String> {
}
