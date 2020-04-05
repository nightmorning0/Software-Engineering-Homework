package com.example.xjtuhelper;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class NewsAdapter extends BaseAdapter {
    private List<News> newsData;
    private LayoutInflater newsInflater; //自定义新闻列表布局

    public NewsAdapter(LayoutInflater inflater, List<News> data){
        this.newsData = data;
        this.newsInflater = inflater;
    }


    // BaseAdapter 定义需要重写的方法
    @Override
    public int getCount(){
        // 返回数据列表的大小
        return newsData.size();
    }

    @Override
    public Object getItem(int position){
        // 获取每条 ListView 中 Item，使用 position 定位
        return position;
    }

    @Override
    public long getItemId(int position){
        // 获取每个 Item 的 ID，仍然用 position 定位
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup){
        // 将数据映射到自定义的 View 中，然后返回 View
        // 获得 ListView 中的 view
        View news_item = newsInflater.inflate(R.layout.newslist_item, null);
        // 获得新闻对象
        News news = newsData.get(position);
        // 获取 layout handle
        ImageView news_pic = (ImageView) news_item.findViewById(R.id.news_pic);
        TextView news_title = (TextView) news_item.findViewById(R.id.news_title);
        TextView news_date = (TextView) news_item.findViewById(R.id.news_date);
        // 将 item 的真实值置入
        news_pic.setImageResource(R.drawable.xiaohui_touming_96);
        news_title.setText(news.getTitle());
        news_date.setText((news.getDate()));
        return news_item;

    }
}
