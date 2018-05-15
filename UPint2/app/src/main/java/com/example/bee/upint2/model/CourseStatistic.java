package com.example.bee.upint2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CourseStatistic implements Serializable {

    @SerializedName("price_per_student")
    @Expose
    String price_per_student;
    @SerializedName("date")
    @Expose
    String date;

    public CourseStatistic(int count, String date){
        this.count = count;
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @SerializedName("count")
    @Expose
    int count;

    public void setHour(float hour) {
        this.hour = hour;
    }

    public float getHour() {
        return hour;
    }

    @SerializedName("hour")
    @Expose
    float hour;



    public void setPrice_per_student(String price_per_student) {
        this.price_per_student = price_per_student;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice_per_student() {
        return price_per_student;
    }

    public String getDate() {
        return date;
    }


}
