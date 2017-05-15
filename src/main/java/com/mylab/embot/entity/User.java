package com.mylab.embot.entity;

public class User {

    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String passId;
    private Representation representation;

    public User(String name, String surname, String email, String passId, Representation representation) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passId = passId;
        this.representation = representation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }
}
