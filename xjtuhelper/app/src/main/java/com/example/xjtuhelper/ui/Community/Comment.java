package com.example.xjtuhelper.ui.Community;

import java.io.Serializable;

public class Comment implements Serializable {
    private String content;
    private String time;
    private String username;

    public Comment(String content, String time, String username) {
        this.content = content;
        this.time = time;
        this.username = username;
    }

    String getContent() { return content; }
    String getTime() { return time; }
    String getUsername() { return username; }
}
