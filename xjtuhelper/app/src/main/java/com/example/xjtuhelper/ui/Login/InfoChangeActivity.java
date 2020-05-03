//package com.example.xjtuhelper.ui.Login;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
//import com.example.xjtuhelper.Application;
//import com.example.xjtuhelper.Constant;
//import com.example.xjtuhelper.MainActivity;
//import com.example.xjtuhelper.R;
//import com.example.xjtuhelper.User;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class InfoChangeActivity extends AppCompatActivity {
//    EditText text_name;  //用户名
//    EditText text_gender;  //性别
//    EditText text_id;  //学号
//    EditText text_pwd;  //密码
//    EditText text_college;  //学院
//    Button button_login;
//
//    String new_name;
//    String new_pwd ;
//    String new_id;
//    String new_college;
//    int new_gender;
//
//    String name;
//    String pwd;
//    String id;
//    String college;
//    int gender;
//
//    RequestQueue connectQueue;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_info_change);
//        // 判定登录状态，决定页面显示
//        SharedPreferences user_info = getSharedPreferences("user_info", MODE_PRIVATE);
//        boolean login = user_info.getBoolean("login", false);
//        if (login) {
//            name = user_info.getString("username", "奥里给");
//            id = user_info.getString("id", "6666666666");
//            college = user_info.getString("college", "沙雕学院");
//            gender = user_info.getInt("gender", Constant.CODE_GENDER_MALE);
//            pwd = user_info.getString("pwd", "aoligibupeiyongyoumima");
//        }
//        else {
//
//        }
//
//        // 信息填写
//        text_name = (EditText) findViewById(R.id.name);  //获取用户名
//        text_pwd = (EditText) findViewById(R.id.pass);  //获取密码
//        text_gender = (EditText) findViewById(R.id.sex);
//        text_id = (EditText) findViewById(R.id.stunum);
//        text_college = (EditText) findViewById(R.id.department);
//        button_login = findViewById(R.id.login);
//
//        new_name = text_name.getText().toString().trim();
//        new_pwd = text_pwd.getText().toString().trim();
//        new_id = text_id.getText().toString().trim();
//        new_college = text_college.getText().toString().trim();
//        new_gender = text_gender.getText().toString().trim();
//
//        button_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 密码 md5 加密
//                try {
//                    MessageDigest md5_instance = MessageDigest.getInstance("MD5");
//                    md5_instance.update(new_pwd.getBytes());
//                    byte[] digest = md5_instance.digest();
//                    StringBuilder builder = new StringBuilder(); // 频繁修改字符串提升性能
//                    for (byte b: digest){
//                        String tmp = Integer.toHexString(b & 0xff);
//                        builder.append(tmp.length() == 1?"0" + tmp: tmp); // 注意拼接占位
//                    }
//                    new_pwd = builder.toString();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//
//                String payload = String.format("{" +
//                        "\"id\":\"%s\"," +
//                        "\"pwd\":\"%s\"," +
//                        "\"college\":\"%s\", " +
//                        "\"gender\":\"%s\", " +
//                        "\"username\":\"%s\"}",
//                        new_id, new_pwd, new_college, new_gender, new_name);
//                // volley 连接
//                // 初始化请求队列
//                connectQueue = Volley.newRequestQueue(LoginActivity.this);
//
//            }
//            });
//    }
//}
