package com.example.xjtuhelper.ui.Community;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.xjtuhelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {
    private List<PersonChat> PersonChat;
    private List<Comment> comments;
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
        CommentsAdapter adapter = new CommentsAdapter(inflater, comments);
        comments_list.setAdapter(adapter);


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
