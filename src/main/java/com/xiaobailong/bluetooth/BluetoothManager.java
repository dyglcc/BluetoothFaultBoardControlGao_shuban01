package com.xiaobailong.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BluetoothManager {

	private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();

	private final String Uuid = "00001101-0000-1000-8000-00805F9B34FB";

	private Context context = null;

	private ArrayList<BluetoothDevice> bluetoothDeviceList = new ArrayList<BluetoothDevice>();

	private BluetoothListener blueToothListener = null;

	private BluetoothSocket bluetoothSocket = null;

	private OutputStream os = null;

	private InputStream is = null;

	private BluetoothPairReceiver bluetoothPairReceiver = null;

	private boolean needAutoBond = true;

	private boolean bluetoothCononected = false;

	private int length = 19;

	public BluetoothManager(Context context, BluetoothListener blueToothListener) {
		this.context = context;
		this.blueToothListener = blueToothListener;
	}

	public void changeContext(Context context,
			BluetoothListener blueToothListener) {
		this.context = context;
		this.blueToothListener = blueToothListener;
	}

	public boolean isBluetoothOpened() {
		return this.mBluetoothAdapter.isEnabled();
	}

	public void openBluetooth() {
		this.mBluetoothAdapter.enable();
	}

	public void closeBluetooth() {
		this.mBluetoothAdapter.disable();
	}

	public void getBondedDevices() {
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				if (isHasBluetoothDevice(device) == false) {
					bluetoothDeviceList.add(device);
				}
			}
		}
	}

	public void search() {

		Thread t = new Thread() {
			@Override
			public void run() {
				IntentFilter filter = new IntentFilter(
						BluetoothDevice.ACTION_FOUND);
				context.registerReceiver(mReceiver, filter);
				filter = new IntentFilter(
						BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
				context.registerReceiver(mReceiver, filter);
				mBluetoothAdapter.startDiscovery();
			}
		};
		t.start();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device != null && isHasBluetoothDevice(device) == false) {
					bluetoothDeviceList.add(device);
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				// search finished
				if (blueToothListener != null) {
					blueToothListener.optionCallBack(
							BluetoothListener.SearchFinished, null);
				}
				context.unregisterReceiver(mReceiver);
			}
		}
	};

	public ArrayList<String> getAllBluetoothName() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < bluetoothDeviceList.size(); i++) {
			list.add(bluetoothDeviceList.get(i).getName());
		}
		return list;
	}

	private boolean isHasBluetoothDevice(BluetoothDevice device) {
		for (int i = 0; i < bluetoothDeviceList.size(); i++) {
			if (device.getAddress().equals(
					bluetoothDeviceList.get(i).getAddress())) {
				return true;
			}
		}
		return false;
	}

	public void connect(int position, String psw) {
		try {
			BluetoothDevice device = this.bluetoothDeviceList.get(position);
			int bondState = device.getBondState();
			if (needAutoBond && bondState != BluetoothDevice.BOND_BONDED) {
				boolean autoBond = autoBond(device, psw);
				if (autoBond == false) {
					error("Bluetooth connect failed !!!");
					return;
				}
			}
			if (bluetoothCononected == false) {
				bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID
						.fromString(Uuid));
				bluetoothSocket.connect();
				bluetoothCononected = true;
			}
			if (this.blueToothListener != null) {
				this.blueToothListener.optionCallBack(
						BluetoothListener.BluetoothConnected, null);
			}
		} catch (IOException e) {
			error("Bluetooth connect failed !!!");
		}
	}

	private boolean autoBond(BluetoothDevice device, String psw) {
		bluetoothPairReceiver = new BluetoothPairReceiver();
		bluetoothPairReceiver.setPsw(psw);
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
		filter.addAction("android.bluetooth.device.action.PAIRING_CANCEL");
		context.registerReceiver(bluetoothPairReceiver, filter);
		boolean isBondSuccess = new BluetoothAutoBond().pair(device, psw);
		if (isBondSuccess) {
			return true;
		} else {
			return false;
		}
	}

	private void error(String error) {
		if (this.blueToothListener != null) {
			this.blueToothListener.log(error);
		}
	}

	public void sendData(final byte[] data, final int id) {
		if (bluetoothCononected == false) {
			error("Blouetooth has not connected !");
			return;
		}
//		Thread t = new Thread() {
//			@Override
//			public void run() {
				try {
					if (os == null) {
						os = bluetoothSocket.getOutputStream();
					}
					os.write(data);
					os.flush();
					Thread.sleep(50);
					if (is == null) {
						is = bluetoothSocket.getInputStream();
					}
					//缓存空间，用以从外部接收数据
					byte[] isData = new byte[length];
					byte[] result = new byte[length];
					int readDataLength = 0;
					int index = 0;
					boolean endRead = true;
					while (endRead && (readDataLength = is.read(isData)) > 0) {
						for (int i = 0; i < readDataLength; i++) {
							result[index] = isData[i];
							index++;
							if (index == length) {
								endRead = false;
								break;
							}
						}
					}
					if (blueToothListener != null) {
						blueToothListener.inputData(result, index--, id);
					}
				} catch (IOException e) {
					e.printStackTrace();
					error("SendData failed !!!");
				} catch (InterruptedException e) {
					e.printStackTrace();
					error("SendData failed !!!");
				}
//			}
//		};
//
//		t.start();
	}

	private void closeIO() throws IOException {
		if (os != null) {
			os.close();
		}
		if (is != null) {
			is.close();
		}
	}

	public void close() {
		try {
			if (bluetoothPairReceiver != null) {
				context.unregisterReceiver(bluetoothPairReceiver);
			}
			closeIO();
			if (bluetoothSocket != null) {
				bluetoothSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isNeedAutoBond() {
		return needAutoBond;
	}

	public void setNeedAutoBond(boolean needAutoBond) {
		this.needAutoBond = needAutoBond;
	}

	public boolean isBluetoothCononected() {
		return bluetoothCononected;
	}
}
