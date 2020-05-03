package com.example.xjtuhelper.ui.Community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xjtuhelper.Application;
import com.example.xjtuhelper.Constant;
import com.example.xjtuhelper.MainActivity;
import com.example.xjtuhelper.R;
import com.example.xjtuhelper.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private Toolbar tb;
    private ChatAdapter chatAdapter;
    private User user_info;
    private List<Comment> comments;
    private RequestQueue connectQueue; // 请求队列

    //声明ListView

    private ListView lv_chat_dialog;

    // 初始化聊天信息
    private List<PersonChat> personChats = new ArrayList<PersonChat>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                     //ListView条目控制在最后一行
                    lv_chat_dialog.setSelection(personChats.size());
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        tb = findViewById(R.id.toolbar_chat);
        tb.setTitle("Chat");
        tb.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tb);
        lv_chat_dialog = (ListView) findViewById(R.id.chat_dia_list);
        Button btn_chat_message_send = (Button) findViewById(R.id.btn_chat_send_msg);
        final EditText et_chat_message = (EditText) findViewById(R.id.chat_msg);

        user_info = ((Application) getApplicationContext()).user_info;
        comments = ((Application) getApplicationContext()).global_comments;
        connectQueue = Volley.newRequestQueue(this);

//setAdapter

        chatAdapter = new ChatAdapter(this, personChats, user_info);
        lv_chat_dialog.setAdapter(chatAdapter);
        comments = ((Application)getApplicationContext()).global_comments;
        for (int i=comments.size() - 1; i>=0; i--) {
            personChats.add(comments.get(i).toPersonChat());
        }

//发送按钮的点击事件

        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(ChatActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = et_chat_message.getText().toString();
                PersonChat personChat = new PersonChat(user_info.getId(), user_info.getName(), msg);
                //加入集合
                personChats.add(personChat);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
                ((Application)getApplicationContext()).comment_is_update = true;
                // 上传评论到服务器
                String payload = String.format("{\"id\":\"%s\",\"passwd\":\"%s\",\"comment\":\"%s\"}",user_info.getId(), user_info.getPwd(), msg);
                String url = Constant.REMOTE_COMMENT_COMMIT + payload;
                StringRequest stringRequest = new StringRequest(url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("TAG", "comment commit successfully");
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
