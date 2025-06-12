package com.example.seeable.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Child implements Serializable {
    String id, fname, lname, notes, parentId;
    MyDate birthday;

    List<Report> reports;

    public Child() {
        reports = new ArrayList<>();
    }

    public Child(String id, String fname, String lname, String notes, String parentId, MyDate birthday, List<Report> reports) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.notes = notes;
        this.parentId = parentId;
        this.birthday = birthday;
        this.reports = reports;
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

    public List<Report> getDailyReports() {
        if (reports == null) {
            reports = new ArrayList<>();
        }
        return reports;
    }

    public void setDailyReports(List<Report> reports) {
        this.reports = reports;
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


    // TODO get report by MyDate


}
