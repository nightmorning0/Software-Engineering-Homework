package com.example.xjtuhelper.ui.Login;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.xjtuhelper.Application;
import com.example.xjtuhelper.Constant;
import com.example.xjtuhelper.MainActivity;
import com.example.xjtuhelper.R;
import com.example.xjtuhelper.User;
import com.example.xjtuhelper.ui.News.News;
import com.example.xjtuhelper.ui.News.NewsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    EditText text_user;  // 学号
    EditText text_pwd;  //密码
    Button mlogin,mreg,mfgt;
    private RequestQueue connectQueue; // 请求队列
    String user;
    String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mlogin = findViewById(R.id.btn_login);
        mreg = findViewById(R.id.btn_reg);
        mfgt = findViewById(R.id.btn_fgt_pswd) ;
        //写代码
        text_user=(EditText) findViewById(R.id.user);  //获取学号
        text_pwd=(EditText) findViewById(R.id.pass);  //获取密码
        //动画效果
        ANIM();

        //修改密码按钮逻辑
        mfgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
        //注册按钮逻辑
        mreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
        // 登录按钮逻辑
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = text_user.getText().toString().trim();
                pwd = text_pwd.getText().toString().trim();

                // 密码 md5 加密
                try {
                    MessageDigest md5_instance = MessageDigest.getInstance("MD5");
                    md5_instance.update(pwd.getBytes());
                    byte[] digest = md5_instance.digest();
                    StringBuilder builder = new StringBuilder(); // 频繁修改字符串提升性能
                    for (byte b: digest){
                        String tmp = Integer.toHexString(b & 0xff);
                        builder.append(tmp.length() == 1?"0" + tmp: tmp); // 注意拼接占位
                    }
                    pwd = builder.toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                String payload = String.format("{\"id\":\"%s\",\"passwd\":\"%s\"}", user, pwd);
                // volley 连接
                // 初始化请求队列
                connectQueue = Volley.newRequestQueue(LoginActivity.this);

                // 从服务器验证登录
                getJSON(new MainActivity.VolleyCallback() {
                    @SuppressLint({"DefaultLocale", "CommitPrefEdits"})
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        int status = response.getInt("status");
                        String db_msg;
                        switch (status){
                            case Constant.STATUS_LOGIN_SUCCESS:
                                String college = response.getString("college");
                                int gender = response.getInt("gender");
                                String username = response.getString("username");
                                Log.e("TAG", "username: "+username);
                                db_msg = "登录成功";
                                // 将信息状态更新到全局
                                ((Application)getApplicationContext()).user_info = new User(username, user, college, gender, pwd);

                                // 将信息缓存，下次登录自动登录
                                SharedPreferences user_info = LoginActivity.this.getSharedPreferences("user_info", MODE_PRIVATE);
                                SharedPreferences.Editor user_info_editor = user_info.edit();
                                user_info_editor.putBoolean("login", true);
                                user_info_editor.putString("username", username);
                                user_info_editor.putString("id", user);
                                user_info_editor.putString("college", college);
                                user_info_editor.putInt("gender", gender);
                                user_info_editor.putString("pwd", pwd);
                                user_info_editor.apply();
                                Intent home = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(home);
                                break;
                            case Constant.STATUS_LOGIN_WRONG_PWD:
                                db_msg = "密码错误";
                                break;
                            case Constant.STATUS_LOGIN_NO_USER:
                                db_msg = "用户不存在";
                                break;
                            default:
                                db_msg = "奇怪的返回值，检查服务器代码";
                        }
                        Toast.makeText(LoginActivity.this, db_msg, Toast.LENGTH_SHORT).show();

                        //((Application)getApplicationContext()).global_news = news;
                    }
                }, Constant.REMOTE_LOGIN + payload);
            }
        });

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
        text_user.setAnimation(animname);
        text_pwd.setAnimation(animpass);
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
    public void getJSON(final MainActivity.VolleyCallback callback, String url) {
        // 用于解决 Volley 异步响应无法返回 response 的问题
        JsonObjectRequest jreq = new JsonObjectRequest(
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }
        );
        connectQueue.add(jreq);
    }
    public interface VolleyCallback {
        // 定义成功响应回调接口
        void onSuccess(JSONObject response) throws JSONException;
    }
}