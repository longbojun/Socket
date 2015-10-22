package com.lb.socket.socket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/10/21.
 */
public class MsgAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public MsgAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return  list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setPadding(5,5,5,5);
        tv.setText(list.get(position));
        tv.setTextSize(15);
        return tv;
    }
}
