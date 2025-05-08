package com.example.seeable.model;

public class Child {
    String id, fname, lname, notes, parentId;
    MyDate birthday;

    public Child() {
    }

    public Child(String id, String fname, String lname, String notes, String parentId, MyDate birthday) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.notes = notes;
        this.parentId = parentId;
        this.birthday = birthday;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public MyDate getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id='" + id + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", notes='" + notes + '\'' +
                ", parentId='" + parentId + '\'' +
                ", birthday=" + birthday +
                '}';
    }


}
