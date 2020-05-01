package com.example.xjtuhelper.ui.Community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xjtuhelper.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private Toolbar tb;
    private ChatAdapter chatAdapter;

    //声明ListView

    private ListView lv_chat_dialog;

    //集合

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

//setAdapter

        chatAdapter = new ChatAdapter(this, personChats);
        lv_chat_dialog.setAdapter(chatAdapter);

//发送按钮的点击事件

        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(ChatActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonChat personChat = new PersonChat();
                //代表自己发送
                personChat.setMeSend(true);
                //得到发送内容
                personChat.setChatMessage(et_chat_message.getText().toString());
                //加入集合
                personChats.add(personChat);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);

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
