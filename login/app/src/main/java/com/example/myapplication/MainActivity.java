package com.example.myapplication;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name;  //用户名
    EditText pass;  //密码
    Button mlogin,mreg,mfgt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mlogin = findViewById(R.id.btn_login);
        mreg = findViewById(R.id.btn_reg);
        mfgt = findViewById(R.id.btn_fgt_pswd) ;
        //写代码
        name=(EditText) findViewById(R.id.name);  //获取用户名
        pass=(EditText) findViewById(R.id.pass);  //获取密码
        //动画效果
        ANIM();

        //修改密码按钮逻辑
        mfgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"修改密码",Toast.LENGTH_SHORT).show();
            }
        });
        //注册按钮逻辑
        mreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"注册",Toast.LENGTH_SHORT).show();
            }
        });


    }
    //登录验证代码
    public void  Check(View v) {
        String mname = "hello";
        String mpass = "1234";
        String user = name.getText().toString().trim();
        String pwd = pass.getText().toString().trim();
        if (user.equals(mname) && pwd.equals(mpass)) {
            Toast.makeText(this, "恭喜，通过", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "很遗憾，继续努力", Toast.LENGTH_SHORT).show();
        }
    }
    private void ANIM(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screen_x = metric.widthPixels;     // 屏幕宽度（像素）
        int screen_y = metric.heightPixels;
        //四个控件的移动逻辑
        Animation animreg = new TranslateAnimation(-202,0,screen_y*(1/2),screen_y*(1/2));
        Animation animlogin = new TranslateAnimation(142,0,screen_y*(1/2),screen_y*(1/2));
        Animation animname = new TranslateAnimation(-280,0,screen_y*(1/2)-60,screen_y*(1/2)-60);
        Animation animpass = new TranslateAnimation(-280,0,screen_y*(1/2)+60,screen_y*(1/2)+60);
        Animation animfgt = new TranslateAnimation(0,0,82,0);
        //四个控件的移动时间
        animreg.setDuration(500);
        animlogin.setDuration(500);
        animname.setDuration(500);
        animpass.setDuration(500);
        animfgt.setDuration(500);
        //四个控件匹配对应的移动逻辑
        mreg.setAnimation(animreg);
        mlogin.setAnimation(animlogin);
        name.setAnimation(animname);
        pass.setAnimation(animpass);
        mfgt.setAnimation(animfgt);
        //防止返回原位
        animreg.setFillEnabled(true);
        animlogin.setFillEnabled(true);
        animname.setFillEnabled(true);
        animpass.setFillEnabled(true);
        animfgt.setFillEnabled(true);
        //防止返回原位
        animreg.setFillAfter(true);
        animlogin.setFillAfter(true);
        animname.setFillAfter(true);
        animpass.setFillAfter(true);
        animfgt.setFillAfter(true);
        //执行动画
        animreg.startNow();
        animlogin.startNow();
        animname.startNow();
        animpass.startNow();
        animfgt.startNow();
    }




}