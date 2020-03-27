package com.example.xjtuhelper;

public class News {
    private String title;
    private String date;
    private String pic_src;
    private String content;
    public News(String title){
        this.title = title;
        this.date = "";
        this.pic_src = "";
        this.content = "";
    }
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

}
