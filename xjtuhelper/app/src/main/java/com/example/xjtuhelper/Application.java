package com.example.xjtuhelper;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xjtuhelper.ui.Community.Comment;
import com.example.xjtuhelper.ui.News.News;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Application extends android.app.Application {
    public int global_current_theme_code;
    public List<News> global_news;
    public List<Comment> global_comments;
    public int getGlobal_current_window = R.id.menu_news;
    public boolean comment_is_update = false;
    public User user_info;
    public static RequestQueue connect_queue;
    public static RequestQueue tmp_queue;
    public boolean user_is_login;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数组
        global_news = new ArrayList<>();
        global_comments = new ArrayList<>();

        // 初始化连接队列
        connect_queue = Volley.newRequestQueue(this);
        tmp_queue = Volley.newRequestQueue(this);
        // 初始化全局时间，用于自动判断是否夜间模式
        int time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(time>=6&&time<21){
            ((Application)getApplicationContext()).global_current_theme_code = AppCompatDelegate.MODE_NIGHT_NO;
        }
        if(time>=21||time<6){
            ((Application)getApplicationContext()).global_current_theme_code = AppCompatDelegate.MODE_NIGHT_YES;
        }
        // 初始化用户信息
        SharedPreferences static_user_info = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = static_user_info.getString("username", "奥里给");
        String id = static_user_info.getString("id", "6666666666");
        String college = static_user_info.getString("college", "沙雕学院");
        int gender = static_user_info.getInt("gender", Constant.CODE_GENDER_MALE);
        String pwd = static_user_info.getString("pwd", "aoligibupeiyongyoumima");

        user_info = new User(username, id, college, gender, pwd);
        user_is_login = static_user_info.getBoolean("login", false);

        // 初始化新闻列表
        newsUpdate();
        // 初始化评论列表
        commentsUpdate();
    }


    public static void getJSON(final VolleyCallback callback, String url) {
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
        connect_queue.add(jreq);
    }
    public interface VolleyCallback {
        // 定义成功响应回调接口
        void onSuccess(JSONObject response) throws JSONException;
    }

    public void newsUpdate(){
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
                    global_news.add(new News(title, date, url, content));
                }
            }
        }, Constant.REMOTE_NEWS_GET);
    }

    public void commentsUpdate(){
        // 从服务器获取评论
        getJSON(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                JSONArray data_list = response.getJSONArray("comments");
                for (int i=0; i < data_list.length(); i++) {
                    JSONObject data = data_list.getJSONObject(i);
                    String comment_username = data.getString("username");
                    String comment_content = data.getString("comment");
                    String comment_time = data.getString("time");
                    String comment_user_id = data.getString("id");
                    global_comments.add(new Comment(comment_content, comment_time, comment_username, comment_user_id));
                }
            }
        }, Constant.REMOTE_COMMENTS_GET);
    }

}
