package com.example.xjtuhelper;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;

public class WelcomeActivity extends Activity{
    private Button btn_close;
    private Button btn_close2;
    Calendar c = Calendar.getInstance();
    int time =c.get(Calendar.HOUR_OF_DAY);
    private boolean main_activity_is_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        main_activity_is_start = false; // 用于判定主活动是否启动，避免点跳过重复启动主活动
//开始界面判断是否采用夜间模式
        if(time>=6&&time<21){
            ((Application)getApplicationContext()).global_current_theme_code = AppCompatDelegate.MODE_NIGHT_NO;
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if(time>=21||time<6){
            ((Application)getApplicationContext()).global_current_theme_code = AppCompatDelegate.MODE_NIGHT_YES;
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        setContentView(R.layout.welcome);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close2 = (Button) findViewById(R.id.btn_close2);

        final Intent home = new Intent(this, MainActivity.class);
        Timer timer = new Timer();

// 定时跳转
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!main_activity_is_start) {
                    main_activity_is_start = true;
                    startActivity(home); //执行
                }
            }
        };
        timer.schedule(task, 1000 * 3);

// 直接跳转
        btn_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!main_activity_is_start) {
                    main_activity_is_start = true;
                    startActivity(home); //执行
                }
            }
        });
        btn_close2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!main_activity_is_start) {
                    main_activity_is_start = true;
                    startActivity(home); //执行
                }
            }
        });
    }
}
