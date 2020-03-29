package com.example.xjtuhelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;


public class MainActivity extends AppCompatActivity {
    private List<String> newstitle;
    private ListView newslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newstitle = new ArrayList<String>();
        newstitle.add("关于启用雨课堂“长江”分平台的通知");
        newstitle.add("浏览器跳转测试");
        for (int i = 3; i < 10; i++){
            newstitle.add("第" + i + "条新闻");
        }
        // 设置 adapter
        ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, newstitle);
        newslist = (ListView) findViewById(R.id.news_list);
        newslist.setAdapter(adapter);
        newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                    startActivity(new Intent(MainActivity.this, NewsContentActivity.class));
                }
                else if (id == 1){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://nightmorning.site")));
                }
            }
        });
    }
}
