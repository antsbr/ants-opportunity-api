package org.ants.opportunity.model;

public enum OpportunityTypeEnum {
 ANIMAL("Animal"), KIDS("Kids");

 String name;
 OpportunityTypeEnum(String name){
    this.name = name;
 }
 
 public String getName() {
     return name;
 }

}