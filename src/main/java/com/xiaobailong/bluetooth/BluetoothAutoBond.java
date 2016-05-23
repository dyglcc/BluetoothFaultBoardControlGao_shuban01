package com.xiaobailong.bluetooth;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class BluetoothAutoBond
{

	private final String Tag = "errorMsg";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception
	{
		Method createBondMethod = btClass.getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean removeBond(Class btClass, BluetoothDevice btDevice) throws Exception
	{
		Method removeBondMethod = btClass.getMethod("removeBond");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean setPin(Class btClass, BluetoothDevice btDevice, String str) throws Exception
	{
		try
		{
			Method removeBondMethod = btClass.getDeclaredMethod("setPin", new Class[] { byte[].class });
			Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice, new Object[] { str.getBytes() });
			Log.d(Tag, "setPin returnValue:" + returnValue);
		}catch(SecurityException e)
		{
			e.printStackTrace();
		}catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean cancelPairingUserInput(Class btClass, BluetoothDevice device) throws Exception
	{
		Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
		Boolean returnValue = (Boolean) createBondMethod.invoke(device);
		return returnValue.booleanValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean cancelBondProcess(Class btClass, BluetoothDevice device) throws Exception
	{
		Method createBondMethod = btClass.getMethod("cancelBondProcess");
		Boolean returnValue = (Boolean) createBondMethod.invoke(device);
		return returnValue.booleanValue();
	}

	@SuppressWarnings({ "rawtypes" })
	public void printAllInform(Class clsShow)
	{
		try
		{
			// 取得所有方法
			Method[] hideMethod = clsShow.getMethods();
			int i = 0;
			for (; i < hideMethod.length; i++)
			{
				Log.d(Tag, "hideMethod::" + hideMethod[i].getName() + ";and the i is:" + i);
			}
			// 取得所有常量
			Field[] allFields = clsShow.getFields();
			for (i = 0; i < allFields.length; i++)
			{
				Log.d(Tag, "allFields::" + allFields[i].getName());
			}
		}catch(SecurityException e)
		{
			e.printStackTrace();
		}catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean pair(BluetoothDevice device, String strPsw)
	{
		boolean result = false;

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		bluetoothAdapter.cancelDiscovery();

		if(!bluetoothAdapter.isEnabled())
		{
			bluetoothAdapter.enable();
		}

		// // 检查蓝牙地址是否有效
		// if(!BluetoothAdapter.checkBluetoothAddress(device.getAddress()))
		// {
		// Log.d(Tag, "devAdd un effient!");
		// }

		// BluetoothDevice device =
		// bluetoothAdapter.getRemoteDevice(device.getAddress());

		if(device.getBondState() != BluetoothDevice.BOND_BONDED)
		{
			try
			{
				Log.d(Tag, "NOT BOND_BONDED");
				setPin(device.getClass(), device, strPsw); 
				createBond(device.getClass(), device);
				result = true;
			}catch(Exception e)
			{
				Log.d(Tag, "setPiN failed!");
				e.printStackTrace();
			}
		}else
		{
			Log.d(Tag, "HAS BOND_BONDED");
			try
			{
				createBond(device.getClass(), device);
				setPin(device.getClass(), device, strPsw); 
				createBond(device.getClass(), device);
				result = true;
			}catch(Exception e)
			{
				Log.d(Tag, "setPiN failed!");
				e.printStackTrace();
			}
		}
		return result;
	}
}
