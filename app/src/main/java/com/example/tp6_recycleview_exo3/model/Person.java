package com.example.tp6_recycleview_exo3.model;

public class Person {

    private int id;
    private String name, firstname;

    public Person(String name, String firstname) {
        this.name = name;
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
