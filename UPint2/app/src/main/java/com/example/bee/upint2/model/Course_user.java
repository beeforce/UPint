package com.example.bee.upint2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bee on 3/7/2018.
 */

public class Course_user implements Serializable {

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("user_id")
    @Expose
    String user_id;

    public Integer getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    @SerializedName("course_id")
    @Expose
    int course_id;
    @SerializedName("count")
    @Expose
    int count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
