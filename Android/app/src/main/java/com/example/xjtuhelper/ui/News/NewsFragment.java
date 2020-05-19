package com.example.xjtuhelper.ui.News;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xjtuhelper.Application;
import com.example.xjtuhelper.Constant;
import com.example.xjtuhelper.MainActivity;
import com.example.xjtuhelper.R;
import com.example.xjtuhelper.ui.Community.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private List<News> news;
    SwipeRefreshLayout refresh;
    NewsAdapter adapter;

    public static NewsFragment newInstance(List<News> news) {
        NewsFragment f = new NewsFragment();
        Bundle args = new Bundle();
        args.putSerializable("news", (Serializable) news);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        if (getArguments() != null) {
            news = (List<News>) getArguments().getSerializable("news");
        }
        else {
            news = new ArrayList<>();
        }
        final ListView news_list = root.findViewById(R.id.news_list);
        adapter = new NewsAdapter(inflater, news);
        news_list.setAdapter(adapter);
        news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.setClass(getContext(), NewsContentActivity.class);
                i.putExtra("news", (Serializable) news.get((int) id));
                startActivity(i);
            }
        });
        refresh = root.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Application.getJSON(new Application.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        news.clear();
                        JSONArray data_list = response.getJSONArray("data");
                        for (int i=0; i < data_list.length(); i++) {
                            JSONObject data = data_list.getJSONObject(i);
                            String title = data.getString("title");
                            String content = data.getString("content");
                            String date = data.getString("date");
                            String url = data.getString("url");
                            news.add(new News(title, date, url, content));
                        }
                        ((Application)getActivity().getApplicationContext()).global_news = news;
                        adapter.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                        Toast.makeText(getActivity(), "刷新成功",Toast.LENGTH_SHORT).show();
                    }
                }, Constant.REMOTE_NEWS_GET);
            }
        });


        return root;


    }

}
