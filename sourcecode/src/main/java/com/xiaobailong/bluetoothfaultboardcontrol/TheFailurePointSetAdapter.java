package com.xiaobailong.bluetoothfaultboardcontrol;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class TheFailurePointSetAdapter extends BaseAdapter implements
        OnClickListener {

    private ArrayList<Relay> list = null;

    private Context context = null;

    private Handler mHandler = null;

    public TheFailurePointSetAdapter(Context context, ArrayList<Relay> list,
                                     Handler mHandler) {
        this.list = list;
        this.context = context;
        this.mHandler = mHandler;
    }

    @Override
    public int getCount() {
        return list.size();
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
        Button button = null;

        if (convertView == null) {
            button = new Button(context);
            convertView = button;
        } else {
            button = (Button) convertView;
        }

        Relay relay = this.list.get(position);

        if (TextUtils.isEmpty(relay.getValue())) {
            button.setText(relay.showId() + "");
        } else {
            button.setText(relay.getValue() + "");
        }

        button.setBackgroundColor(relay.getState());
        button.setTag(relay);
        button.setId(relay.getId());
        button.setOnClickListener(this);
        button.setTextSize(10);
        // button.setWidth(40/2*3);
        // button.setHeight(40/2*3);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        Message msg = this.mHandler.obtainMessage();
        msg.arg1 = v.getId();
        msg.obj = v.getTag();
        this.mHandler.sendMessage(msg);
    }

}
