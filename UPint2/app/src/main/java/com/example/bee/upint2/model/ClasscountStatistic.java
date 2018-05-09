package com.example.bee.upint2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClasscountStatistic implements Serializable {

    @SerializedName("date")
    @Expose
    String date;

    public String getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @SerializedName("class_count")
    @Expose
    int count;


}
