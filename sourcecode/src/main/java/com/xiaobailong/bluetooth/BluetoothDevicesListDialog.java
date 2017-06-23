package com.xiaobailong.bluetooth;

import java.util.ArrayList;

import com.xiaobailong.bluetoothfaultboardcontrol.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BluetoothDevicesListDialog extends Dialog implements OnItemClickListener,View.OnClickListener
{

	private BluetoothListener blueToothListener = null;

	public BluetoothDevicesListDialog(Context context, ArrayList<String> list, BluetoothListener blueToothListener)
	{
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bluetooth_list);
		this.blueToothListener = blueToothListener;
		ListView listView = (ListView) findViewById(R.id.listView_bluetooth);
		BluetoothDevicesListAdapter bluetoothDevicesListAdapter = new BluetoothDevicesListAdapter(context, list);
		listView.setAdapter(bluetoothDevicesListAdapter);
		listView.setOnItemClickListener(this);
		
		findViewById(R.id.Button_Search_BluetoothDevices).setOnClickListener(this);
		findViewById(R.id.Button_BluetoothMenu_Back).setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
	{
		if(blueToothListener!=null)
		{
			BluetoothMessage msg=new BluetoothMessage();
			msg.setArg1(position);
			blueToothListener.optionCallBack(BluetoothListener.BluetoothDevicesSelected, msg);
		}
		dismiss();
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.Button_BluetoothMenu_Back:
			dismiss();
			break;
		case R.id.Button_Search_BluetoothDevices:
			blueToothListener.optionCallBack(BluetoothListener.BluetoothSearch, null);
			dismiss();
			break;
		}
	}

}
