package com.example.xjtuhelper;

public class User {
    private String name;
    private String college;
    private int gender;

    User(String name, String college, int gender) {
        this.name = name;
        this.college = college;
        this.gender = gender;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public String getName() {
        return name;
    }
    public String getCollege() {
        return college;
    }
    public int getGender() {
        return gender;
    }
}
