package org.ants.opportunity.repository;

import org.ants.opportunity.model.Type;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "Type", path = "type")
public interface TypeRepository extends MongoRepository<Type, String> {

    @Cacheable({"type"})
    Type findByName(String name);

    @Cacheable({"type"})
    List<Type> findById(ObjectId id);

    @Cacheable({"type"})
    List<Type> findAll();

    @CachePut({"type"})
    Type save(Type type);

    @CacheEvict({"type"})
    void delete(Type type);

}
