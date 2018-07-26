package org.ants.opportunity.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;

public class Type {

    @Id
    public String id;
    @NotBlank
    @Size(min = 3, max = 50)
    public String name;

    public Type() {}

    public Type(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
