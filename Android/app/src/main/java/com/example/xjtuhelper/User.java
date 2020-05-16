package com.example.xjtuhelper;

public class User {
    private String name;
    private String id;
    private String college;
    private int gender;
    private String pwd;

    public User(String name, String id, String college, int gender, String pwd) {
        this.name = name;
        this.college = college;
        this.gender = gender;
        this.id = id;
        this.pwd = pwd;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public void setGender(int gender) { this.gender = gender; }
    public void setId(String id) {this.id = id;}

    public String getName() {
        return name;
    }
    public String getCollege() {
        return college;
    }
    public int getGender() { return gender; }
    public String getId() {return id;}
    public String getPwd() { return pwd; }
}
