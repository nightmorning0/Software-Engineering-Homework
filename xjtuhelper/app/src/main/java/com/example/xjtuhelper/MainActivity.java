package com.example.xjtuhelper;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xjtuhelper.ui.Community.CommunityFragment;
import com.example.xjtuhelper.ui.Map.MapFragment;
import com.example.xjtuhelper.ui.News.News;
import com.example.xjtuhelper.ui.News.NewsAdapter;
import com.example.xjtuhelper.ui.News.NewsContentActivity;
import com.example.xjtuhelper.ui.News.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private List<News> news;
    private RequestQueue connectQueue; // 请求队列
    //ListView news_list;
    //LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int current_window = ((Application)getApplicationContext()).getGlobal_current_window;
        int current_theme = ((Application)getApplicationContext()).global_current_theme_code;
        Log.e("TAG", "onCreate, current_window="+current_window);
        AppCompatDelegate.setDefaultNightMode(current_theme);
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("XJTU Helper");//设置主标题
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTextIcons));
        setSupportActionBar(toolbar);

        // 新闻初始化
        if ( ((Application)getApplicationContext()).global_news == null) {
            news = new ArrayList<>();
            // volley 连接
            // 初始化请求队列
            connectQueue = Volley.newRequestQueue(this);
            JsonObjectRequest newsRequest;

            // 从服务器获取今日的数据条目数
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
                    ((Application)getApplicationContext()).global_news = news;
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, NewsFragment.newInstance(news)).commit();
                }
            }, Constant.REMOTE_NEWS_GET);
        }
        else {
            news =  ((Application)getApplicationContext()).global_news;
        }

        // 底部导航
        BottomNavigationView bottom_nav_view = findViewById(R.id.nav_view);
        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                switch (item.getItemId()) {
                    case R.id.menu_news:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, NewsFragment.newInstance(news)).commit();
                        ((Application)getApplicationContext()).getGlobal_current_window = R.id.menu_news;
                        break;
                    case R.id.menu_maps:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new MapFragment()).commit();
                        ((Application)getApplicationContext()).getGlobal_current_window = R.id.menu_maps;
                        break;
                    case R.id.menu_community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new CommunityFragment()).commit();
                        ((Application)getApplicationContext()).getGlobal_current_window = R.id.menu_community;
                        break;
                }
                return true;
            }
        });

        // 初始化窗口位置
        switch (current_window) {
            case R.id.menu_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, NewsFragment.newInstance(news)).commit();
                break;
            case R.id.menu_maps:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new MapFragment()).commit();
                break;
            case R.id.menu_community:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new CommunityFragment()).commit();
                break;
        }



    }

    //绑定menu:menu_content
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    //设置夜间模式选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (id == R.id.tb_nightmode){
            if(mode == Configuration.UI_MODE_NIGHT_NO) {
                Toast.makeText(this, "夜间模式", Toast.LENGTH_SHORT).show();
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                ((Application)getApplicationContext()).global_current_theme_code = AppCompatDelegate.MODE_NIGHT_YES;
                recreate();
            }
            if(mode == Configuration.UI_MODE_NIGHT_YES) {
                Toast.makeText(this, "日间模式", Toast.LENGTH_SHORT).show();
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                ((Application)getApplicationContext()).global_current_theme_code = AppCompatDelegate.MODE_NIGHT_NO;
                recreate();
            }
        }
        return super.onOptionsItemSelected(item);
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

