package com.example.xjtuhelper.ui.Community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xjtuhelper.R;

import java.util.List;

public class CommentsAdapter extends BaseAdapter {
    private List<Comment> commentData;
    private LayoutInflater commentInflater; //自定义评论列表布局

    public CommentsAdapter(LayoutInflater inflater, List<Comment> data){
        this.commentData = data;
        this.commentInflater = inflater;
    }

    // BaseAdapter 定义需要重写的方法
    @Override
    public int getCount(){
        // 返回数据列表的大小
        return commentData.size();
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
        View comment_item = commentInflater.inflate(R.layout.commentlist_item, null);
        // 获得评论对象
        Comment comment = commentData.get(position);
        // 获取 layout handle
        ImageView news_pic = (ImageView) comment_item.findViewById(R.id.comment_pic);
        TextView username = (TextView) comment_item.findViewById(R.id.comment_username);
        TextView comment_content = (TextView) comment_item.findViewById(R.id.comment_content);
        TextView comment_time = (TextView) comment_item.findViewById(R.id.comment_time);
        // 将 item 的真实值置入
        username.setText(comment.getUsername());
        comment_content.setText(comment.getContent());
        comment_time.setText(comment.getTime());
        return comment_item;
    }

    public static String ToDBC(String input) {
        char[] c =input.toCharArray();
        for (int i = 0;i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
