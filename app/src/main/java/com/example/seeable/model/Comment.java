package com.example.seeable.model;

public class Comment {
    String id;
    Child  child;
    User user;
    String date;

    String details;

    public Comment(String id, Child child, User userTeam, String date, String details) {
        this.id = id;
        this.child = child;
        this.user = userTeam;
        this.date = date;
        this.details = details;
    }

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public User getUserTeam() {
        return user;
    }

    public void setUserTeam(User userTeam) {
        this.user = userTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", child=" + child +
                ", userTeam=" + user +
                ", date='" + date + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
