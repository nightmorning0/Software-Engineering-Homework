package com.example.xjtuhelper.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xjtuhelper.Application;
import com.example.xjtuhelper.Constant;
import com.example.xjtuhelper.MainActivity;
import com.example.xjtuhelper.R;
import com.example.xjtuhelper.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InfoChangeActivity extends AppCompatActivity {
    EditText text_name;  //用户名
    EditText text_gender;  //性别
    EditText text_id;  //学号
    EditText text_pwd;  //密码
    EditText text_college;  //学院
    Button button_login;

    String new_name;
    String new_pwd ;
    String new_id;
    String new_college;
    int new_gender;

    String name;
    String pwd;
    String id;
    String college;
    int gender;

    RequestQueue connectQueue;
    Intent home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_change);

        // 初始化请求队列
        connectQueue = Volley.newRequestQueue(InfoChangeActivity.this);

        home = new Intent(this, MainActivity.class);

        // 判定登录状态，决定页面显示
        SharedPreferences user_info = getSharedPreferences("user_info", MODE_PRIVATE);
        boolean login = user_info.getBoolean("login", false);
        if (login) {
            name = user_info.getString("username", "奥里给");
            id = user_info.getString("id", "6666666666");
            college = user_info.getString("college", "沙雕学院");
            gender = user_info.getInt("gender", Constant.CODE_GENDER_MALE);
            pwd = user_info.getString("pwd", "aoligibupeiyongyoumima");
        }
        else {

        }

        // 信息填写
        text_name = (EditText) findViewById(R.id.name);  //获取用户名
        text_pwd = (EditText) findViewById(R.id.pass);  //获取密码
        text_gender = (EditText) findViewById(R.id.sex);
        text_id = (EditText) findViewById(R.id.stunum);
        text_college = (EditText) findViewById(R.id.department);
        button_login = findViewById(R.id.login);



        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_name = text_name.getText().toString().trim();
                new_pwd = text_pwd.getText().toString().trim();
                new_id = text_id.getText().toString().trim();
                new_college = text_college.getText().toString().trim();
                new_gender = 0;
                // 密码 md5 加密
                try {
                    MessageDigest md5_instance = MessageDigest.getInstance("MD5");
                    md5_instance.update(new_pwd.getBytes());
                    byte[] digest = md5_instance.digest();
                    StringBuilder builder = new StringBuilder(); // 频繁修改字符串提升性能
                    for (byte b: digest){
                        String tmp = Integer.toHexString(b & 0xff);
                        builder.append(tmp.length() == 1?"0" + tmp: tmp); // 注意拼接占位
                    }
                    new_pwd = builder.toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                String payload = String.format("{" +
                        "\"id\":\"%s\"," +
                        "\"passwd\":\"%s\"," +
                        "\"college\":\"%s\", " +
                        "\"gender\":\"%s\", " +
                        "\"username\":\"%s\"}",
                        new_id, new_pwd, new_college, new_gender, new_name);
                String url = Constant.REMOTE_INFOCHANGE + payload;
                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Integer.valueOf(response) == Constant.STATUS_GENERAL_SUCCESS) {
                            // 将信息状态更新到全局
                            ((Application)getApplicationContext()).user_info = new User(new_name, new_id, new_college, new_gender, new_pwd);

                            // 将信息缓存，下次登录自动登录
                            SharedPreferences user_info = InfoChangeActivity.this.getSharedPreferences("user_info", MODE_PRIVATE);
                            SharedPreferences.Editor user_info_editor = user_info.edit();
                            user_info_editor.putBoolean("login", true);
                            user_info_editor.putString("username", new_name);
                            user_info_editor.putString("id", new_id);
                            user_info_editor.putString("college", new_college);
                            user_info_editor.putInt("gender", new_gender);
                            user_info_editor.putString("pwd", new_pwd);
                            user_info_editor.apply();
                            startActivity(home);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "onErrorResponse");
                    }
                });
                connectQueue.add(request);

            }
            });
    }
}
