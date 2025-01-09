package com.example.seeable.model;

public class Child {
    String id;
    String fname;
    String lname;
    User parent;
    String birthDate;

    String details;

    public Child(String id, String fname, String lname, User parent, String birthDate, String details) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.parent = parent;
        this.birthDate = birthDate;
        this.details = details;
    }

    public Child() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id='" + id + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", parent=" + parent +
                ", birthDate='" + birthDate + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
