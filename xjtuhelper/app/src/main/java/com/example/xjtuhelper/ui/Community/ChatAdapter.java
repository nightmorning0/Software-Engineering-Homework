package com.example.xjtuhelper.ui.Community;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xjtuhelper.R;
import com.example.xjtuhelper.User;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<PersonChat> lists;
    private User user_info;

    public ChatAdapter(Context context, List<PersonChat> lists, User user_info) {
        super();
        this.context = context;
        this.lists = lists;
        this.user_info = user_info;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lists.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        return arg1;
    }



    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
