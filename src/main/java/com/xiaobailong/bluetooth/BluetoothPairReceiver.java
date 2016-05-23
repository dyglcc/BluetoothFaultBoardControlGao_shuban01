package com.xiaobailong.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothPairReceiver extends BroadcastReceiver
{

	private String psw = "1234";

	private BluetoothAutoBond bluetoothAutoBond = new BluetoothAutoBond();

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if(intent.getAction().equals("android.bluetooth.device.action.PAIRING_REQUEST")||intent.getAction().equals("android.bluetooth.device.action.PAIRING_CANCEL"))
		{
			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			try
			{
				// 手机和蓝牙采集器配对
				bluetoothAutoBond.setPin(device.getClass(), device, psw);
				bluetoothAutoBond.createBond(device.getClass(), device);
				bluetoothAutoBond.cancelPairingUserInput(device.getClass(), device);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	public String getPsw()
	{
		return psw;
	}

	public void setPsw(String psw)
	{
		this.psw = psw;
	}
}
