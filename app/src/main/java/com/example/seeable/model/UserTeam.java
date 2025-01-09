package com.example.seeable.model;

public class UserTeam extends User {
    String position;

    public UserTeam(String position) {
        this.position = position;
    }

    public UserTeam(String id, String fname, String lname, String phone, String email, String password, String position) {
        super(id, fname, lname, phone, email, password);
        this.position = position;
    }

    public UserTeam() {
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "UserTeam{" +
                "position='" + position + '\'' +
                ", id='" + id + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
