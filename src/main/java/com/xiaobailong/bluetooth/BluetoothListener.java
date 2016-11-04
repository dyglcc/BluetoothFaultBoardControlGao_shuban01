package com.xiaobailong.bluetooth;

public interface BluetoothListener
{

	public static final int SearchFinished=0;
	public static final int BluetoothDevicesSelected=1;
	public static final int BluetoothSearch=2;
	public static final int BluetoothConnected=3;
	
	void optionCallBack(int optionId, BluetoothMessage msg);
	
	void inputData(byte[] data, int readDataLength, int id);
	
	void log(String errorMsg);
}
