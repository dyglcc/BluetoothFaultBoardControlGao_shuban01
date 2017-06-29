package com.xiaobailong.bluetoothfaultboardcontrol;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

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

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fault,null);
            holder.button = (TextView) convertView.findViewById(R.id.button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Relay relay = this.list.get(position);

        if (TextUtils.isEmpty(relay.getValue())) {
            holder.button.setText(relay.showId() + "");
        } else {
            holder.button.setText(relay.getValue() + "");
        }

        holder.button.setBackgroundColor(relay.getState());
        holder.button.setTag(relay);
        holder.button.setId(relay.getId());
        holder.button.setOnClickListener(this);
        // 原来是10
//        button.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.grid_text_size));
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

    final class ViewHolder {

        TextView button;
    }
}
