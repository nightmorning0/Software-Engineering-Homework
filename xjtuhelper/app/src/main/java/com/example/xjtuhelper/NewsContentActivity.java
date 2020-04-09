package com.example.xjtuhelper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class NewsContentActivity extends AppCompatActivity {
    private TextView Title;
    private TextView Detail;
    private TextView MainText;
    private News news;
    //
    //0->日间  1->夜间
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    //绑定menu:menu_content
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }
    //SharedPreferences mSharedPreferences = getSharedPreferences("daynight", Context.MODE_PRIVATE);
    //SharedPreferences.Editor editor = mSharedPreferences.edit();
    //editor.putString("daynight", day);
    //editor.commit();

    //设置夜间模式选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (id == R.id.tb_nightmode){
            if(mode == Configuration.UI_MODE_NIGHT_NO) {
                Toast.makeText(this, "夜间模式", Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }
            if(mode == Configuration.UI_MODE_NIGHT_YES) {
                Toast.makeText(this, "日间模式", Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        Toolbar toolbar = findViewById(R.id.toolbar_newscontent);
        // 获取新闻数据
        Intent i = getIntent();
        news = (News) i.getSerializableExtra("news");

        //Toolbar设置主标题
        toolbar.setTitle("请填写该通知所在目录");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ScrollView sc = findViewById(R.id.nc_sc);

        //设置返回顶部的FAB
        FloatingActionButton fab = findViewById(R.id.btn_nc_tothetop);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc.post(new Runnable() {

                        @Override
                        public void run() {
                            sc.post(new Runnable() {
                                public void run() {
                                    sc.fullScroll(ScrollView.FOCUS_UP);
                                }
                            });
                        }
                    });

                }
            });

        //Toolbar中设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置字体
        Title = findViewById(R.id.nc_tv_title);
        Detail = findViewById(R.id.nc_tv_detail);
        MainText = findViewById(R.id.nc_tv_maintext);
        Typeface HanyiDicSong = Typeface.createFromAsset(getAssets(),"font/HanYi_dic_song.ttf");
        Title.setTypeface(HanyiDicSong);
        Detail.setTypeface(HanyiDicSong);
        MainText.setTypeface(HanyiDicSong);

        //设置内容
        Title.setText(news.getTitle());
        MainText.setText(news.getContent());
        Detail.setText(news.getDate());

    }





}
