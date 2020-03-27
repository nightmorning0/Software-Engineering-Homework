package com.example.xjtuhelper;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class WelcomeActivity extends Activity{
    private Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        final Intent home = new Intent(this, MainActivity.class);
        Timer timer = new Timer();
        btn_close = (Button) findViewById(R.id.btn_close);

// 定时跳转
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(home); //执行
            }
        };
        timer.schedule(task, 1000 * 3);

// 直接跳转
        btn_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                startActivity(home);
            }
        });

    }
}
