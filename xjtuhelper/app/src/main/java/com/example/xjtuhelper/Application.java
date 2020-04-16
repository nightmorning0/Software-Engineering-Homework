package com.example.xjtuhelper;

import com.example.xjtuhelper.ui.News.News;

import java.util.List;

public class Application extends android.app.Application {
    public int global_current_theme_code;
    public List<News> global_news;
    public int getGlobal_current_window = R.id.menu_news;
}
