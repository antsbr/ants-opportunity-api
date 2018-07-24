package org.ants.opportunity.model;

import org.springframework.data.annotation.Id;

public class Type {

    @Id
    public String id;
    public String name;

    public Type() {}

    public Type(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
