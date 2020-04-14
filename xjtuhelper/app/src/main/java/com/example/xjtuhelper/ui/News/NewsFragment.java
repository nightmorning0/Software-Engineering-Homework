package com.example.xjtuhelper.ui.News;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xjtuhelper.MainActivity;
import com.example.xjtuhelper.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private List<News> news;

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
        ListView news_list = root.findViewById(R.id.news_list);
        NewsAdapter adapter = new NewsAdapter(inflater, news);
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
        return root;
    }
}
