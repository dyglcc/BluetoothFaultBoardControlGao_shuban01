package com.xiaobailong.bluetoothfaultboardcontrol;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiaobailong.tools.SpDataUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginActivity extends BaseActivity {

	Button logigBtn;
	EditText idEdit;
	EditText pwdEdit;
	ImageView brandIV=null;
	RelativeLayout rl=null;
	
	private String savePath=null;
	private String companyFileName="CompanyInfo.txt";
	private String companyBrandFileName="brand.png";
	private String backgroundFileName="background.jpg";
	private String companyName=null;
	private boolean hasSdcard=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		// initHandler();
		// initData();
		// initView();
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//		if (Build.VERSION.SDK_INT >= 23) {
//			String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//			ActivityCompat.requestPermissions(this, mPermissionList, 123);
//		}
		initLoginView();
	}

	public void initLoginView() {

		getSdcardPath();
		
		rl=(RelativeLayout)findViewById(R.id.login_Background);
		if(savePath!=null&&hasSdcard){
			File file=new File(savePath+backgroundFileName);
			if(file.exists()){
				rl.setBackground(new BitmapDrawable(savePath+backgroundFileName));
			}
		}
		logigBtn = (Button) findViewById(R.id.login_loging_btn);
		// passwordstate = (Button)findViewById(R.id.passwordstate);//passworld
		// show state
		loadCompanyInfo();
		idEdit = (EditText) findViewById(R.id.login_id); // 帐号输入框
		if(companyName!=null){
			idEdit.setText(companyName);
		}
		idEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				saveCompanyInfo(s.toString());
			}
		});
		pwdEdit = (EditText) findViewById(R.id.login_password); // 密码输入框

		if (logigBtn != null) {
			logigBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					checkLoginInfo();
				}
			});
		}
		
		brandIV=(ImageView)findViewById(R.id.img_brand);
		if(savePath!=null&&hasSdcard){
			File file=new File(savePath+companyBrandFileName);
			if(file.exists()){
				brandIV.setBackground(new BitmapDrawable(savePath+companyBrandFileName));
			}
		}
	}
	
	protected void loadCompanyInfo(){
		if(hasSdcard==false){
			return;
		}
		String companyInfoFilePath=savePath+companyFileName;
		File savePathFile=new File(savePath);
		if(savePathFile.exists()==false){
			savePathFile.mkdirs();
		}
		File companyInfoFile=new File(companyInfoFilePath);
		if(companyInfoFile.exists()==false){
			return;
		}
		try {
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(companyInfoFile)));
			companyName=br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void saveCompanyInfo(String content){
		if(hasSdcard==false){
			return;
		}
		String companyInfoFilePath=savePath+companyFileName;
		File savePathFile=new File(savePath);
		if(savePathFile.exists()==false){
			savePathFile.mkdirs();
		}
		try {
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(companyInfoFilePath))));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getSdcardPath() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			hasSdcard=true;
			savePath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/autoblue/";
//			Toast.makeText(this, sdcardPath, Toast.LENGTH_LONG).show();
		}
	}

	protected void checkLoginInfo() {
//		startMain();
		String name;
		String password;
		name = idEdit.getText().toString();
		password = pwdEdit.getText().toString();
		if (name.equals("") || password.equals("")) { // 用户名或密码为空
														// Tools.showDialog("确定",
														// "", "提示",
														// "用户名或密码不能为空", 1, 0);
														// showDialog("用户名或密码不能为空",
														// 1);// 显示对话框
			Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
		} else {// 用户名密码不为空
			boolean superMan = password.equals("000000");
			boolean rightPass = false;
			String pass = SpDataUtils.getUserPwd();
			if (!TextUtils.isEmpty(pass) && pass.equals(password)) {
				rightPass = true;
			}
			if (superMan || rightPass) {
				startMain();
			} else {
				Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
			}

		}
	}

	private void startMain() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
