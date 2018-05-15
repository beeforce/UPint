package com.example.bee.upint2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Teacher implements Serializable {


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDate_graduated() {
        return Date_graduated;
    }

    public void setDate_graduated(String date_graduated) {
        Date_graduated = date_graduated;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSearch_recent() {
        return search_recent;
    }

    public void setSearch_recent(String search_recent) {
        this.search_recent = search_recent;
    }

    public String getLocation_city() {
        return location_city;
    }

    public void setLocation_city(String location_city) {
        this.location_city = location_city;
    }

    public String getUniversity_can_teach() {
        return university_can_teach;
    }

    public void setUniversity_can_teach(String university_can_teach) {
        this.university_can_teach = university_can_teach;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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
    @SerializedName("search_recent")
    @Expose
    String search_recent;
    @SerializedName("location_city")
    @Expose
    String location_city;
    @SerializedName("university_can_teach")
    @Expose
    String university_can_teach;
    @SerializedName("id_card")
    @Expose
    String id_card;
    @SerializedName("introduce")
    @Expose
    String introduce;


}
