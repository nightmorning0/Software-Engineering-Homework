package com.example.xjtuhelper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class NewsContentActivity extends AppCompatActivity {
    private TextView Title;
    private TextView Detail;
    private TextView MainText;
    private News news;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

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
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }
            if(mode == Configuration.UI_MODE_NIGHT_YES) {
                Toast.makeText(this, "日间模式", Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        Toolbar toolbar = findViewById(R.id.toolbar_newscontent);
        // 获取新闻数据
        Intent i = getIntent();
        news = (News) i.getSerializableExtra("news");

        //Toolbar设置主标题
        toolbar.setTitle("请填写该通知所在目录");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ScrollView sc = findViewById(R.id.nc_sc);

        //设置返回顶部的FAB
        FloatingActionButton fab = findViewById(R.id.btn_nc_tothetop);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc.post(new Runnable() {

                        @Override
                        public void run() {
                            sc.post(new Runnable() {
                                public void run() {
                                    sc.fullScroll(ScrollView.FOCUS_UP);
                                }
                            });
                        }
                    });

                }
            });

        //Toolbar中设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        //设置字体
        Title = findViewById(R.id.nc_tv_title);
        Detail = findViewById(R.id.nc_tv_detail);
        MainText = findViewById(R.id.nc_tv_maintext);
        Typeface HanyiDicSong = Typeface.createFromAsset(getAssets(),"font/HanYi_dic_song.ttf");
        Title.setTypeface(HanyiDicSong);
        Detail.setTypeface(HanyiDicSong);
        MainText.setTypeface(HanyiDicSong);

        //设置内容
        Title.setText(news.getTitle());
        MainText.setText(news.getContent());
        Detail.setText(news.getDate());

        //获得MainText的实际宽度、字体大小等信息
        MainText.getViewTreeObserver().addOnGlobalLayoutListener(new OnTvGlobalLayoutListener());
    }

    //
    private class OnTvGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            MainText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            final String newText = autoSplitText(MainText);
            if (!TextUtils.isEmpty(newText)) {
                MainText.setText(newText);
            }
        }
    }
    private String autoSplitText(final TextView tv) {
        final String rawText = tv.getText().toString();//原始文本
        final Paint tvPaint = tv.getPaint();//paint，包含字体等信息
        final int tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight();//控件可用宽度

        //将原始文本按行拆分
        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        return sbNewText.toString();
    }




}
