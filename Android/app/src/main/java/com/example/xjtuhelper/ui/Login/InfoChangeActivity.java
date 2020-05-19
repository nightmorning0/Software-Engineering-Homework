package com.example.xjtuhelper.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.xjtuhelper.Application;
import com.example.xjtuhelper.Constant;
import com.example.xjtuhelper.MainActivity;
import com.example.xjtuhelper.R;
import com.example.xjtuhelper.User;

import android.widget.AdapterView.OnItemSelectedListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class InfoChangeActivity extends AppCompatActivity {
    EditText text_name;  //用户名
    EditText text_id;  //学号
    EditText text_old_pwd; // 原有密码，仅在修改信息时生效
    EditText text_pwd;  //密码
    Spinner spin_gender;  //性别
    Spinner spin_college;  //学院
    Button button_login;
    ImageView BG;
    String old_pwd;

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
    Intent home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_change);


        home = new Intent(this, MainActivity.class);


        // 布局初始化
        Resources res = getResources();
        ArrayList<String> str_list_gender = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.sex0)));
        ArrayList<String> str_list_college = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.depart0)));
        spin_gender = findViewById(R.id.sex1);
        spin_college = findViewById(R.id.depart1);
        text_name = (EditText) findViewById(R.id.name);  //获取用户名
        text_pwd = (EditText) findViewById(R.id.pass);  //获取密码
        text_old_pwd = (EditText) findViewById(R.id.old_pass);
        text_id = (EditText) findViewById(R.id.stunum);
        button_login = findViewById(R.id.button_map);
        BG=findViewById(R.id.reg_chg);
        ArrayAdapter<String> adp_gender = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str_list_gender);//创建Arrayadapter适配器
        ArrayAdapter<String> adp_college = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str_list_college);
        spin_gender.setAdapter(adp_gender);
        spin_college.setAdapter(adp_college);

        // 判定登录状态，决定页面显示
        SharedPreferences user_info = getSharedPreferences("user_info", MODE_PRIVATE);
        final boolean login = user_info.getBoolean("login", false);
        if (login) {
            // 信息修改，重新生成布局
            text_id.setFocusable(false);
            button_login.setText("修改并登录");
            text_old_pwd.setVisibility(View.VISIBLE);
            text_pwd.setHint("新密码");
            BG.setImageResource(R.drawable.change_info);
            name = user_info.getString("username", "奥里给");
            id = user_info.getString("id", "6666666666");
            college = user_info.getString("college", "沙雕学院");
            gender = user_info.getInt("gender", Constant.CODE_GENDER_MALE);
            pwd = user_info.getString("pwd", "aoligibupeiyongyoumima");
            // 置入原始值
            text_id.setText(id);
            text_name.setText(name);
            spin_college.setSelection(str_list_college.indexOf(college));
            String str_gender = (gender==Constant.CODE_GENDER_MALE)?"男":"女";
            spin_gender.setSelection((str_list_gender.indexOf(str_gender)));
        }

        spin_gender.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str_gender = parent.getItemAtPosition(position).toString();
                if (str_gender.equals("男")) {
                    new_gender = Constant.CODE_GENDER_MALE;
                }
                else if (str_gender.equals("女")) {
                    new_gender = Constant.CODE_GENDER_FEMALE;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spin_college.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new_college = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_name = text_name.getText().toString().trim();
                old_pwd = text_old_pwd.getText().toString().trim();
                new_pwd = text_pwd.getText().toString().trim();
                new_id = text_id.getText().toString().trim();

                if (login) {
                    if (new_name.length() == 0 || new_name.length() == 0 ||
                            new_pwd.length() == 0 || old_pwd.length() ==0) {
                        Toast.makeText(InfoChangeActivity.this, "不能有空项", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    if (new_name.length() == 0 || new_name.length() == 0 || new_pwd.length() == 0){
                        Toast.makeText(InfoChangeActivity.this, "不能有空项", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                // 密码 md5 加密
                try {
                    if (login) {
                        MessageDigest old_md5_instance = MessageDigest.getInstance("MD5");
                        old_md5_instance.update(old_pwd.getBytes());
                        byte[] old_digest = old_md5_instance.digest();
                        StringBuilder old_builder = new StringBuilder(); // 频繁修改字符串提升性能
                        for (byte b: old_digest){
                            String tmp = Integer.toHexString(b & 0xff);
                            old_builder.append(tmp.length() == 1?"0" + tmp: tmp); // 注意拼接占位
                        }
                        old_pwd = old_builder.toString();
                    }

                    MessageDigest new_md5_instance = MessageDigest.getInstance("MD5");
                    new_md5_instance.update(new_pwd.getBytes());
                    byte[] new_digest = new_md5_instance.digest();
                    StringBuilder new_builder = new StringBuilder(); // 频繁修改字符串提升性能
                    for (byte b: new_digest){
                        String tmp = Integer.toHexString(b & 0xff);
                        new_builder.append(tmp.length() == 1?"0" + tmp: tmp); // 注意拼接占位
                    }
                    new_pwd = new_builder.toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                String url ;
                String payload = String.format("{" +
                                "\"id\":\"%s\"," +
                                "\"passwd\":\"%s\"," +
                                "\"college\":\"%s\", " +
                                "\"gender\":\"%s\", " +
                                "\"username\":\"%s\"}",
                        new_id, new_pwd, new_college, String.valueOf(new_gender), new_name);
                if (login) {
                    if (!pwd.equals(old_pwd)) {
                        Toast.makeText(InfoChangeActivity.this, "旧密码错误", Toast.LENGTH_SHORT).show();
                        text_old_pwd.setText("");
                        return;
                    }
                    url = Constant.REMOTE_INFOCHANGE + payload;
                }
                else {
                    url = Constant.REMOTE_LOGUP + payload;
                }


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
                Application.connect_queue.add(request);
            }
            });
    }
}
