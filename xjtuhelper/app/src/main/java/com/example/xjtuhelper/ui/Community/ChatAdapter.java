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

    /**
     * 是否是自己发送的消息
     */
//    public static interface IMsgViewType {
//        int IMVT_COM_MSG = 0;// 收到对方的消息
//        int IMVT_TO_MSG = 1;// 自己发送出去的消息
//    }

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

    /**
     * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
     */
//    public int getItemViewType(int position) {
//        PersonChat entity = lists.get(position);
//
//        if (entity.isMeSend(user_info.getId())) {// 收到的消息
//            return IMsgViewType.IMVT_COM_MSG;
//        } else {// 自己发送的消息
//            return IMsgViewType.IMVT_TO_MSG;
//        }
//    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        HolderView holderView = null;
        PersonChat entity = lists.get(arg0);
        boolean isMeSend = entity.isMeSend(user_info.getId());
        if (holderView == null) {
            holderView = new HolderView();
            if (isMeSend) {
                arg1 = View.inflate(context, R.layout.chat_dia_right_item,
                        null);
                TextView name_view = arg1.findViewById(R.id.chat_user_name_right);
                name_view.setText(entity.getName());
                holderView.tv_chat_me_message = (TextView) arg1
                        .findViewById(R.id.tv_chat_msg_left);
                holderView.tv_chat_me_message.setText(entity.getChatMessage());
            } else {
                arg1 = View.inflate(context,R.layout.chat_dia_left_item,
                        null);

            }
            arg1.setTag(holderView);
        } else {
            holderView = (HolderView) arg1.getTag();
        }

        return arg1;
    }

    class HolderView {
        TextView tv_chat_me_message;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
