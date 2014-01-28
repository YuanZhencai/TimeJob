package com.wcs.jasper.xml.model;

import java.util.List;

public class Department {

    private String name;
    private List<Person> persons;
    
    public Department() {
    }
    
    public Department(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Person> getPersons() {
        return persons;
    }
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
}
