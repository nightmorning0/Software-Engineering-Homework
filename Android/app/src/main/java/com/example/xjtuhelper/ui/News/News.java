package com.example.xjtuhelper.ui.News;

import com.example.xjtuhelper.R;

import java.io.Serializable;

public class News implements Serializable {
    private String title;
    private String date;
    private int pic_src;
    private String content;
    private String url;
    public News(String title, String date, String url, String content){
        // 测试使用
        this.title = title;
        this.date = date;
        this.pic_src = R.drawable.xiaohui_touming_small;
        this.content = content;
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
