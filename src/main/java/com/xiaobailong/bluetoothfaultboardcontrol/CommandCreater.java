package com.xiaobailong.bluetoothfaultboardcontrol;

public class CommandCreater
{

	public static final byte Start=1;
	public static final byte ShutDown=2;
	public static final byte StateIsRead=3;
	public static final byte SetAll=4;
	public static final byte ClearAll=5;
	public static final byte Test=6;
	public static final byte StartUp=7;
	public static final byte StartDown=8;
	public static final byte FireUp=9;
	public static final byte FireDown=10;
	
	private byte[] command=new byte[5];
	
	public byte[] create(byte type,byte id)
	{
		command[0]=(byte) 0xff;
		command[1]=(byte) 0xaa;
		switch(type)
		{
		case Start:
			if(MainActivity.TableState==MainActivity.BreakTable){
				command[2]=0x10;
			}else if(MainActivity.TableState==MainActivity.ShortTable){
				command[2]=0x20;
			}else if(MainActivity.TableState==MainActivity.FalseTable){
				command[2]=0x30;
			}
			command[3]=id;
			break;
		case ShutDown:
			if(MainActivity.TableState==MainActivity.BreakTable){
				command[2]=0x11;
			}else if(MainActivity.TableState==MainActivity.ShortTable){
				command[2]=0x21;
			}else if(MainActivity.TableState==MainActivity.FalseTable){
				command[2]=0x31;
			}
			command[3]=id;
			break;
		case StateIsRead:
			if(MainActivity.TableState==MainActivity.BreakTable){
				command[2]=(byte) 0x81;
			}else if(MainActivity.TableState==MainActivity.ShortTable){
				command[2]=(byte) 0x82;
			}else if(MainActivity.TableState==MainActivity.FalseTable){
				command[2]=(byte) 0x83;
			}
			command[3]=0;
			break;
		case SetAll:
			if(MainActivity.TableState==MainActivity.BreakTable){
				command[2]=0x12;
			}else if(MainActivity.TableState==MainActivity.ShortTable){
				command[2]=0x22;
			}else if(MainActivity.TableState==MainActivity.FalseTable){
				command[2]=0x32;
			}
			command[3]=0;
			break;
		case ClearAll:
			if(MainActivity.TableState==MainActivity.BreakTable){
				command[2]=0x13;
			}else if(MainActivity.TableState==MainActivity.ShortTable){
				command[2]=0x23;
			}else if(MainActivity.TableState==MainActivity.FalseTable){
				command[2]=0x33;
			}
			command[3]=0;
			break;
		case Test:
			command[2]=0x01;
			command[3]=id;
			break;
		case StartUp:
			command[2]=(byte) 0xF0;
			command[3]=0;
			break;
		case StartDown:
			command[2]=(byte) 0xF1;
			command[3]=0;
			break;
		case FireUp:
			command[2]=(byte) 0xE0;
			command[3]=0;
			break;
		case FireDown:
			command[2]=(byte) 0xE1;
			command[3]=0;
			break;
		}
		command[4]=(byte) (~(command[0]+command[1]+command[2]+command[3])+1);
		return command;
	}
}
