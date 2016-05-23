package com.xiaobailong.bluetooth;

import java.util.ArrayList;

import com.xiaobailong.bluetoothfaultboardcontrol.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BluetoothDevicesListAdapter extends BaseAdapter{
	
	private ArrayList<String> list=null;
	
	private LayoutInflater layoutInflater=null;
	
	private class ListItem{
		TextView textView=null;
	}
	
	public BluetoothDevicesListAdapter(Context context,ArrayList<String> list){
		
		this.list=list;
		layoutInflater=LayoutInflater.from(context);
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
		ListItem listItem=null;
		if(convertView==null){
			listItem=new ListItem();
			convertView=layoutInflater.inflate(R.layout.bluetooth_list_item,null);
			listItem.textView=(TextView)convertView.findViewById(R.id.textView_ListView_Item);
			convertView.setTag(listItem);
		}else{
			listItem=(ListItem) convertView.getTag();
		}
		
		listItem.textView.setText(list.get(position));
		return convertView;
	}

}
