package com.example.xjtuhelper;

public class News {
    private String title;
    private String date;
    private int pic_src;
    private String content;
    private String url;
    public News(String title, String date, String url){
        this.title = title;
        this.date = date;
        this.pic_src = R.drawable.xiaohui;
        this.content = "content";
        this.url = url;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDate(String date) { this.date = date; }
    public void setPic(int src) { this.pic_src = src; }
    public void setContent(String content) { this.content = content; }
    public void setUrl(String url) { this.url = url; }
    public String getTitle() { return this.title; }
    public String getDate() { return this.date; }
    public int getPic() { return this.pic_src; }
    public String getContent() { return this.content; }
    public String getUrl() { return this.url; }
}
