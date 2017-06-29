package com.xiaobailong.bluetoothfaultboardcontrol;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaobailong.tools.SpDataUtils;

/**
 * Created by dongyuangui on 2017/6/1.
 */

public class PasswordSettingActivity extends BaseActivity {

    EditText title;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_pass_setting);

        save = (Button) findViewById(R.id.save);
        title = (EditText) findViewById(R.id.title);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    public void save() {
        String str = title.getText().toString();
        if (!TextUtils.isEmpty(str)) {
            SpDataUtils.saveUserPwd(str);
            finish();
        } else {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }
    }
}
