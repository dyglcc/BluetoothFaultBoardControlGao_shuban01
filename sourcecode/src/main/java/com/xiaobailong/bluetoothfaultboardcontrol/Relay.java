package com.xiaobailong.bluetoothfaultboardcontrol;

public class Relay
{

	public static final int Red=0xffff0000;
	public static final int Yellow=0xffffff00;
	public static final int Green=0xff00ff00;
//	public static final int Gray=0xff7c7c7c;

	private int state=Green;

	private int id=0;
	private int showId=0;


//	private Relay colleague;

	public Relay(int id,int showId,int state)
	{
		this.id=id;
		this.state=state;
		this.showId=showId;
//		colleague=null;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
//		if(this.state != Gray){
		this.state = state;
//		}

	}

	//	public void setForceState(int state)
//	{
//		this.state = state;
//	}
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	public int showId()
	{
		return showId;
	}

	public void setShowId(int id)
	{
		this.id = showId;
	}

	//	public void setColleague(Relay colleague)
//	{
//		this.colleague=colleague;
//	}
//	public Relay colleague()
//	{
//		return colleague;
//	}
	private int type;
	private String value;
	// 是否是题目
	private boolean isExamination = false;

	public boolean isStdentClick() {
		return stdentClick;
	}

	public void setStdentClick(boolean stdentClick) {
		this.stdentClick = stdentClick;
	}

	private boolean stdentClick = false;
	public boolean isExamination() {
		return isExamination;
	}

	public void setExamination(boolean examination) {
		isExamination = examination;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
