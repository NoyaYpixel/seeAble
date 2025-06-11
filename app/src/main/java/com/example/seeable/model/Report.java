package com.example.seeable.model;

import java.io.Serializable;
import java.util.Objects;

public class Report implements Comparable<Report>, Serializable {

    private String childId;
    private MyDate date;

    // if Boolean is Null, it wasn't selected
    private Boolean arrived, feelingGood, eatMorning, drank, slept;

    public Report() {
        date = MyDate.now();
    }

    public Report(String childId, MyDate date, Boolean arrived, Boolean feelingGood, Boolean eatMorning, Boolean drank, Boolean slept) {
        this.childId = childId;
        this.date = date;
        this.arrived = arrived;
        this.feelingGood = feelingGood;
        this.eatMorning = eatMorning;
        this.drank = drank;
        this.slept = slept;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public MyDate getDate() {
        return date;
    }

    public void setDate(MyDate date) {
        this.date = date;
    }

    public Boolean getArrived() {
        return arrived;
    }

    public void setArrived(Boolean arrived) {
        this.arrived = arrived;
    }

    public Boolean getFeelingGood() {
        return feelingGood;
    }

    public void setFeelingGood(Boolean feelingGood) {
        this.feelingGood = feelingGood;
    }

    public Boolean getEatMorning() {
        return eatMorning;
    }

    public void setEatMorning(Boolean eatMorning) {
        this.eatMorning = eatMorning;
    }

    public Boolean getDrank() {
        return drank;
    }

    public void setDrank(Boolean drank) {
        this.drank = drank;
    }

    public Boolean getSlept() {
        return slept;
    }

    public void setSlept(Boolean slept) {
        this.slept = slept;
    }

    @Override
    public String toString() {
        return "Report{" +
                "childId='" + childId + '\'' +
                ", date=" + date +
                ", arrived=" + arrived +
                ", feelingGood=" + feelingGood +
                ", eatMorning=" + eatMorning +
                ", drank=" + drank +
                ", slept=" + slept +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Report that = (Report) o;
        return Objects.equals(childId, that.childId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childId, date);
    }

    @Override
    public int compareTo(Report o) {
        return this.date.compareTo(o.date);
    }
}
