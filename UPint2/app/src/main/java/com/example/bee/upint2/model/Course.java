package com.example.bee.upint2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bee on 2/21/2018.
 */

public class Course implements Serializable {


    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("user_id")
    @Expose
    String user_id;
    @SerializedName("course_name")
    @Expose
    String course_name;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("price_per_student")
    @Expose
    String price_per_student;
    @SerializedName("date")
    @Expose
    String date;
    @SerializedName("start_time")
    @Expose
    String start_time;
    @SerializedName("finish_time")
    @Expose
    String finish_time;
    @SerializedName("target_university")
    @Expose
    String target_university;
    @SerializedName("target_major")
    @Expose
    String target_major;
    @SerializedName("target_years")
    @Expose
    String target_years;
    @SerializedName("terms")
    @Expose
    String terms;
    @SerializedName("level_of_difficult")
    @Expose
    String level_of_difficult;
    @SerializedName("total_student")
    @Expose
    Integer total_student;
    @SerializedName("tags")
    @Expose
    String tags;
    @SerializedName("place")
    @Expose
    String place;
    @SerializedName("course_image")
    @Expose
    String course_image_path;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice_per_student(String price_per_student) {
        this.price_per_student = price_per_student;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public void setTarget_university(String target_university) {
        this.target_university = target_university;
    }

    public void setTarget_major(String target_major) {
        this.target_major = target_major;
    }

    public void setTarget_years(String target_years) {
        this.target_years = target_years;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public void setLevel_of_difficult(String level_of_difficult) {
        this.level_of_difficult = level_of_difficult;
    }

    public void setTotal_student(Integer total_student) {
        this.total_student = total_student;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setCourse_image_path(String course_image_path) {
        this.course_image_path = course_image_path;
    }

    public Integer getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice_per_student() {
        return price_per_student;
    }

    public String getDate() {
        return date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getTarget_university() {
        return target_university;
    }

    public String getTarget_major() {
        return target_major;
    }

    public String getTarget_years() {
        return target_years;
    }

    public String getTerms() {
        return terms;
    }

    public String getLevel_of_difficult() {
        return level_of_difficult;
    }

    public Integer getTotal_student() {
        return total_student;
    }

    public String getTags() {
        return tags;
    }

    public String getPlace() {
        return place;
    }

    public String getCourse_image_path() {
        return course_image_path;
    }



}
