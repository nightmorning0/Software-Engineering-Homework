package com.example.xjtuhelper.ui.Community;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xjtuhelper.Application;
import com.example.xjtuhelper.Constant;
import com.example.xjtuhelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {
    private List<Comment> comments;
    private SwipeRefreshLayout refresh;
    public CommunityFragment() {
    }

    public static CommunityFragment newInstance(List<Comment> comments) {
        CommunityFragment f = new CommunityFragment();
        Bundle args = new Bundle();
        args.putSerializable("comments", (Serializable) comments);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_community, container, false);
        if (getArguments() != null) {
            comments = (List<Comment>) getArguments().getSerializable("comments");
        }
        else {
            comments = new ArrayList<>();
        }
        final ListView comments_list = root.findViewById(R.id.comment_list);
        final CommentsAdapter adapter = new CommentsAdapter(inflater, comments);
        comments_list.setAdapter(adapter);

        refresh = root.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Application.getJSON(new Application.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        comments.clear();
                        JSONArray data_list = response.getJSONArray("comments");
                        for (int i=0; i < data_list.length(); i++) {
                            JSONObject data = data_list.getJSONObject(i);
                            String comment_username = data.getString("username");
                            String comment_content = data.getString("comment");
                            String comment_time = data.getString("time");
                            String comment_user_id = data.getString("id");
                            comments.add(new Comment(comment_content, comment_time, comment_username, comment_user_id));
                        }
                        ((Application)getActivity().getApplicationContext()).global_comments = comments;
                        adapter.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                        Toast.makeText(getActivity(), "刷新成功",Toast.LENGTH_SHORT).show();
                    }
                }, Constant.REMOTE_COMMENTS_GET);
            }
        });

        FloatingActionButton cmt = root.findViewById(R.id.fab_add_cmt);
        cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getContext(), ChatActivity.class);

                startActivity(i);

            }
        });
        return root;
    }
}
