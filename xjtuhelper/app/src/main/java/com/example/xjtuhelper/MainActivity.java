package com.example.xjtuhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xjtuhelper.ui.Community.CommunityFragment;
import com.example.xjtuhelper.ui.Login.InfoChangeActivity;
import com.example.xjtuhelper.ui.Login.LoginActivity;
import com.example.xjtuhelper.ui.Map.MapFragment;
import com.example.xjtuhelper.ui.News.News;
import com.example.xjtuhelper.ui.News.NewsFragment;
import com.example.xjtuhelper.ui.Community.Comment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {
    private List<News> news;
    private List<Comment> comments;
    private DrawerLayout mDrawerLayout;
    private User user_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int current_window = ((Application)getApplicationContext()).getGlobal_current_window;
        int current_theme = ((Application)getApplicationContext()).global_current_theme_code;
        Log.e("TAG", "onCreate, current_window="+current_window);
        AppCompatDelegate.setDefaultNightMode(current_theme);
        super.onCreate(null);
        //全屏模式（想全屏就把下面的注释去掉）
        //hideActionBar();
        //setFullScreen();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setNavigationIcon(R.drawable.ic_dashboard_24dp);
        toolbar.setTitle("XJTU Helper");//设置主标题
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTextIcons));
        setSupportActionBar(toolbar);

        // 用户信息初始化
        user_info = ((Application) getApplicationContext()).user_info;

        // 新闻初始化
        news =  ((Application)getApplicationContext()).global_news;


        // Comments 初始化
        comments =  ((Application)getApplicationContext()).global_comments;

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, CommunityFragment.newInstance(comments)).commit();
                        ((Application)getApplicationContext()).getGlobal_current_window = R.id.menu_community;
                        break;
                }
                return true;
            }
        })
        ;
        //左部导航栏和点击事件
        mDrawerLayout = findViewById(R.id.main_drawer);
        final NavigationView left_nav_view = findViewById(R.id.left_nav_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(left_nav_view);
                // test
                TextView username = findViewById(R.id.username);
                TextView college = findViewById(R.id.college);
                username.setText(user_info.getName());
                college.setText(user_info.getCollege());
            }
        });

        left_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //在这里处理item的点击事件
                int id = item.getItemId();
                if (id == R.id.user_info){
                    Intent logup_intent = new Intent(MainActivity.this, InfoChangeActivity.class);
                    startActivity((logup_intent));
                }
                if (id == R.id.log_out){
                    // 清空用户信息并跳转登录界面
                    SharedPreferences user_info = getSharedPreferences("user_info", MODE_PRIVATE);
                    SharedPreferences.Editor user_info_editor = user_info.edit();
                    user_info_editor.clear();
                    user_info_editor.apply();
                    Intent login_intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(login_intent);
                    finish();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, CommunityFragment.newInstance(comments)).commit();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        user_info = ((Application) getApplicationContext()).user_info;
        news =  ((Application)getApplicationContext()).global_news;
        comments =  ((Application)getApplicationContext()).global_comments;
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
                ((Application)getApplicationContext()).global_current_theme_code = AppCompatDelegate.MODE_NIGHT_YES;
                recreate();
            }
            if(mode == Configuration.UI_MODE_NIGHT_YES) {
                Toast.makeText(this, "日间模式", Toast.LENGTH_SHORT).show();
                ((Application)getApplicationContext()).global_current_theme_code = AppCompatDelegate.MODE_NIGHT_NO;
                recreate();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * hide action bar
     */
    private void hideActionBar() {
        // Hide UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * set the activity display in full screen
     */
    private void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}

