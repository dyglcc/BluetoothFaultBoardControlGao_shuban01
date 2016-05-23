package com.xiaobailong.bluetoothfaultboardcontrol;

public class DataParser
{

	private byte[] data=new byte[19];
	
	private byte[] state=new byte[120];
	
	private final int StateDataLength=15;
	
	public byte[] parse(byte[] data)
	{
		this.data=data;
		return parseState();
	}
	
	private byte[] parseState()
	{
		for (int i = 3; i < StateDataLength+3; i++)
		{
			byte[] stateTemp=null;
			stateTemp=getBitsFormByte(this.data[i]);
			for (int j = 0; j < 8; j++)
			{
				if(j+(i-3)*8==state.length)
				{
					break;
				}
				state[j+(i-3)*8]=stateTemp[7-j];
			}
		}
		return this.state;
	}
	
	/**
	 * 将byte转换为一个长度为8的byte数组,数组每个值代表原byte的一个bit
	 */
	private byte[] getBitsFormByte(byte b)
	{
		byte[] array = new byte[8];
		for (int i = 0; i<8; i++)
		{
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}
}
