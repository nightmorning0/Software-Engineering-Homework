package com.example.xjtuhelper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class NewsContentActivity extends AppCompatActivity {
    private TextView Title;
    private TextView Detail;
    private TextView MainText;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        Toolbar toolbar = findViewById(R.id.toolbar_newscontent);
        //设置主标题
        toolbar.setTitle("请填写该通知所在目录");
        //设置图标
        toolbar.setLogo(R.drawable.xiaohui_touming);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ScrollView sc = findViewById(R.id.nc_sc);
        FloatingActionButton fab = findViewById(R.id.btn_nc_tothetop);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc.post(new Runnable() {

                        @Override
                        public void run() {
                            sc.post(new Runnable() {
                                public void run() {
                                    // 滚动至顶部
                                    sc.fullScroll(ScrollView.FOCUS_UP);
                                }
                            });
                        }
                    });

                }
            });
        //设置返回键
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
    }
}
