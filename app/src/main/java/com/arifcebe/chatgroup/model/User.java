package com.arifcebe.chatgroup.model;

/**
 * Created by arifcebe on 30/10/15.
 */
public class User {
    private int birthYear;
    private String fullName;

    public User() {
    }


    public User(int birthYear, String fullName) {
        this.birthYear = birthYear;
        this.fullName = fullName;
    }


    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
