package com.example.bee.upint2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bee on 2/7/2018.
 */

public class UserProfile implements Serializable {


    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setDate_graduated(String date_graduated) {
        Date_graduated = date_graduated;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public String getDate_graduated() {
        return Date_graduated;
    }

    public String getState() {
        return state;
    }

    public String getImage() {
        return image;
    }

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("first_name")
    @Expose
    String first_name;
    @SerializedName("last_name")
    @Expose
    String last_name;
    @SerializedName("phone_number")
    @Expose
    String phone_number;
    @SerializedName("school")
    @Expose
    String school;
    @SerializedName("major")
    @Expose
    String major;
    @SerializedName("Date_graduated")
    @Expose
    String Date_graduated;
    @SerializedName("state")
    @Expose
    String state;
    @SerializedName("image")
    @Expose
    String image;




}
