package com.example.xjtuhelper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class NewsContentActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        Toolbar toolbar = findViewById(R.id.toolbar_newscontent);
        toolbar.setTitle("请填写该通知所在目录");//设置主标题
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
