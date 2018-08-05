package org.ants.opportunity.model;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Opportunity {

    @Id
    public ObjectId id;

    @NotBlank
    @Size(min = 3, max = 255)
    public String name;

    @NotNull
    public Type type;

    @NotNull
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    public GeoJsonPoint location;

    public Opportunity(ObjectId id, String name, Type type, GeoJsonPoint location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public Opportunity() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(@NotNull Type type) {
        this.type = type;
    }

    @NotNull
    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(@NotNull GeoJsonPoint location) {
        this.location = location;
    }
}
