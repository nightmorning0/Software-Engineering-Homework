package com.example.xjtuhelper.ui.Community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xjtuhelper.Application;
import com.example.xjtuhelper.Constant;
import com.example.xjtuhelper.R;
import com.example.xjtuhelper.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {
    private Toolbar tb;
    private User user_info;
    private String msg;
    private RequestQueue connectQueue; // 请求队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_send_cmt);
        tb = findViewById(R.id.toolbar_chat);
        tb.setTitle("动态");
        tb.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tb);
        Button btn_chat_message_send = (Button) findViewById(R.id.btn_chat_send_msg);
        final EditText et_chat_message = (EditText) findViewById(R.id.chat_msg);

        user_info = ((Application) getApplicationContext()).user_info;
        connectQueue = Volley.newRequestQueue(this);


//发送按钮的点击事件

        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(ChatActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                msg = et_chat_message.getText().toString();
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                ((Application)getApplicationContext()).comment_is_update = true;
                // 上传评论到服务器
                String payload = String.format("{\"id\":\"%s\",\"passwd\":\"%s\",\"comment\":\"%s\"}",user_info.getId(), user_info.getPwd(), msg);
                String url = Constant.REMOTE_COMMENT_COMMIT + payload;
                StringRequest stringRequest = new StringRequest(url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (Integer.valueOf(response) == Constant.STATUS_GENERAL_SUCCESS) {
                                    ((Application) getApplicationContext()).comment_is_update = true;
                                    Comment new_comment = new Comment(msg, "刚刚", user_info.getName(), user_info.getId());
                                    ((Application) getApplicationContext()).global_comments.add(0, new_comment);
                                    Log.d("TAG", "comment commit successfully");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("TAG", "comment commit failed");
                            }
                        }
                );
                connectQueue.add(stringRequest);
                //延时关闭
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                };
                timer.schedule(task, 300 * 1);

            }
        });
//Toolbar中设置返回键
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
