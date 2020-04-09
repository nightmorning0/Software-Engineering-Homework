package com.example.xjtuhelper;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.view.View;
import android.widget.AdapterView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private List<News> news;
    private RequestQueue connectQueue; // 请求队列
    ListView news_list;
    LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("XJTU Helper");//设置主标题
        setSupportActionBar(toolbar);

        // 视图框架初始化
        news_list = (ListView) findViewById((R.id.news_list));
        inflater = getLayoutInflater();
        news = new ArrayList<>();

        // 初始化视图
        NewsAdapter adapter = new NewsAdapter(inflater, news);
        news_list.setAdapter(adapter);


        // volley 连接
        // 初始化请求队列
        connectQueue = Volley.newRequestQueue(this);
        JsonObjectRequest newsRequest;

//         从服务器获取今日的数据条目数
        getJSON(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                // 获取新闻信息
                JSONArray data_list = response.getJSONArray("data");
                for (int i=0; i < data_list.length(); i++) {
                    JSONObject data = data_list.getJSONObject(i);
                    String title = data.getString("title");
                    String content = data.getString("content");
                    String date = data.getString("date");
                    String url = data.getString("url");
                    news.add(new News(title, date, url, content));
                }

                // 更新视图
                NewsAdapter adapter = new NewsAdapter(inflater, news);
                news_list.setAdapter(adapter);

                news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // 先用浏览原网页的形式混过去
//                        int i = (int) id;
//                        String url = news.get(i).getUrl();
//                        startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(url)) );
                        Intent i = new Intent();
                        i.setClass(MainActivity.this, NewsContentActivity.class);
                        i.putExtra("news", (Serializable) news.get((int) id));
                        startActivity(i);
                    }
                });

            }
        }, Constant.REMOTE_NEWS_GET);
    }


    public void getJSON(final VolleyCallback callback, String url) {
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

