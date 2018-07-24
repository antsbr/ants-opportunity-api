package org.ants.opportunity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public class Opportunity {

    @Id
    public String id;
    public String name;
    public Type type;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    public GeoJsonPoint location;

    public Opportunity(String id, String name, Type type, GeoJsonPoint location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public Opportunity() {}
}
