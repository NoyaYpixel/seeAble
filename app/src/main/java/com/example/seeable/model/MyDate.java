package com.example.seeable.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MyDate implements Comparable<MyDate>, Serializable {

    public int year, month, day;

    public MyDate() {
        year = 1970;
        month = 0; // 0 to 11
        day = 1;
    }

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static MyDate now() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new MyDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @NonNull
    @Override
    public String toString() {
        return  year+"/"+month+"/"+day;
        //"MyDate{" +
                //"year=" + year +
                //", month=" + month +
                //", day=" + day +
                //'}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyDate myDate = (MyDate) o;
        return year == myDate.year && month == myDate.month && day == myDate.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public int compareTo(MyDate o) {
        if (this.year != o.year) {
            return Integer.compare(this.year, o.year); // Compare years first
        }
        if (this.month != o.month) {
            return Integer.compare(this.month, o.month); // If years are the same, compare months
        }
        return Integer.compare(this.day, o.day); // If both years and months are the same, compare days
    }
}
