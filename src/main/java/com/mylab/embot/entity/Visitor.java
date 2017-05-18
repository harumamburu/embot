package com.mylab.embot.entity;

public class Visitor {

    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String passId;
    private Representation representation;

    public Visitor() {
    }

    public Visitor(String name, String lastName, String email, String passId, Representation representation) {
        this.name = name;
        this.lastName = lastName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Visitor{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", passId='" + passId + '\'' +
                ", representation=" + representation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visitor visitor = (Visitor) o;

        return passId.equals(visitor.passId);
    }

    @Override
    public int hashCode() {
        return passId.hashCode();
    }
}
