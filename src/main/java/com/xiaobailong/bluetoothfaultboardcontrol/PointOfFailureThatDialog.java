package com.xiaobailong.bluetoothfaultboardcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PointOfFailureThatDialog extends Dialog implements android.view.View.OnClickListener
{

	private Context context = null;

	//private String[] relayInfoIntroduceArray =null;// new String[100];
	private List<String> relayInfoIntroduceArray =null;// new String[100];
	public static final String savePath =  Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ "autoblue" +File.separator;

	public PointOfFailureThatDialog(Context context)
	{
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
		setContentView(R.layout.point_of_failure_that);
		findViewById(R.id.Button_Point_Of_Failure_BT).setOnClickListener(this);
		File file =getFile();
		if(file!=null)
		{
			relayInfoIntroduceArray = new ArrayList<String>();
			inputData(file);
			
			if(relayInfoIntroduceArray.size()==0)
			{
				relayInfoIntroduceArray.add("存储卡上：");
				relayInfoIntroduceArray.add("/autoblue/failures.txt");
				relayInfoIntroduceArray.add("文件不符合标准!");
			}
			
		}
		else
		{
			//Toast.makeText(this,"存储卡上没有/autoblue/failure.txt文件", Toast.LENGTH_SHORT).show();
			relayInfoIntroduceArray = new ArrayList<String>();
			relayInfoIntroduceArray.add("存储卡上：");
			relayInfoIntroduceArray.add("/autoblue/failures.txt");
			relayInfoIntroduceArray.add("不存在");
			//TextView view = (TextView) findViewById(R.id.texttip);
			//view.setVisibility(View.VISIBLE);
			
		}
		ListView lv = (ListView) findViewById(R.id.LV_Point_Of_Failure_List);
		ArrayAdapter<String> relayInfoIntroduceArrayAdapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, relayInfoIntroduceArray);
		lv.setAdapter(relayInfoIntroduceArrayAdapter);
		//lv.
		//lv.setVisibility(View.VISIBLE);
	
	}

	private void inputData(File file)
	{
		try
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
			String line=null;
			//int index=0;
			while((line=br.readLine())!=null)
			{
				if(line.startsWith("$")==true)
				{
					relayInfoIntroduceArray.add(line.substring(1));
				}
			}
			br.close();
		}catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}catch(NotFoundException e)
		{
			e.printStackTrace();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.Button_Point_Of_Failure_BT:
			dismiss();
			break;
		}
	}
	
	 private static File getFile()
	  {
	    File file = new File(savePath+"failures.txt");
	    if (file.exists())
	      return file;
	    file = new File(savePath);
	    if (file.exists())
		      return null;
	    new File(savePath).mkdirs();
	    return null;
	  }

}
