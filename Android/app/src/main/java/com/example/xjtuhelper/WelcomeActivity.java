package com.example.xjtuhelper;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.xjtuhelper.ui.Login.LoginActivity;

public class WelcomeActivity extends Activity{
    private Button btn_close;
    private Button btn_close2;
    private boolean main_activity_is_start;
    private Intent next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        main_activity_is_start = false; // 用于判定主活动是否启动，避免点跳过重复启动主活动
        setContentView(R.layout.welcome);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close2 = (Button) findViewById(R.id.btn_close2);

        // 判定登录状态，决定跳转页面
        boolean login = ((Application) getApplicationContext()).user_is_login;

        if (login) {
            next = new Intent(this, MainActivity.class);
        }
        else {
            next = new Intent(this, LoginActivity.class);
        }

        Timer timer = new Timer();

// 定时跳转
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!main_activity_is_start) {
                    main_activity_is_start = true;
                    startActivity(next); //执行
                }
            }
        };
        timer.schedule(task, 1000 * 1);

// 直接跳转
        btn_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!main_activity_is_start) {
                    main_activity_is_start = true;
                    startActivity(next); //执行
                }
            }
        });
        btn_close2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!main_activity_is_start) {
                    main_activity_is_start = true;
                    startActivity(next); //执行
                }
            }
        });
    }
}
