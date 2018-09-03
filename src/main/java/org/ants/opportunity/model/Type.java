package org.ants.opportunity.model;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;

public class Type {

    @Id
    public ObjectId id;
    @NotBlank
    @Size(min = 3, max = 50)
    public String name;

    public Type() {}

    public Type(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
