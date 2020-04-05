package com.example.xjtuhelper;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;


public class MainActivity extends AppCompatActivity {
    private List<News> news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("XjtuHelper");//设置主标题
        setSupportActionBar(toolbar);

        this.news = new ArrayList<>();
        getData();
        ListView newslist = (ListView) findViewById((R.id.news_list));


        // 简易版设置 adapter
        /*ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, newstitle);*/

        // 设置自定义 adapter
        LayoutInflater inflater = getLayoutInflater();
        NewsAdapter adapter = new NewsAdapter(inflater, news);


        newslist.setAdapter(adapter);
        news = this.news;
        newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // startActivity(new Intent(MainActivity.this, NewsContentActivity.class));
                // 先用浏览原网页的形式混过去
                int i = (int) id;
                String url = news.get(i).getUrl();
                startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(url)) );
            }
        });
    }
    private void getData(){
        // 仅限测试时使用
        News news;
        for(int i = 0; i < 10; i++){
             news = new News("关于2020年清明节期间教学工作安排的通知", "2020-03-30", "http://dean.xjtu.edu.cn/info/1015/8542.htm");
            this.news.add(news);
        }
    }
}
