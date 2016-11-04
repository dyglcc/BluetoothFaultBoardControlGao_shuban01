package com.xiaobailong.titile;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaobailong.bluetoothfaultboardcontrol.BaseActivity;
import com.xiaobailong.bluetoothfaultboardcontrol.MainActivity;
import com.xiaobailong.bluetoothfaultboardcontrol.R;
import com.xiaobailong.tools.ConstValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by dongyuangui on 2016/11/2.
 */

public class WriteTitleActivity extends BaseActivity implements View.OnClickListener {
    private Button ButtonSave;
    private EditText et;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_change_title);
        ButtonSave = (Button) findViewById(R.id.save);
        et = (EditText) findViewById(R.id.title);
        ButtonSave.setOnClickListener(this);
    }

    private void retunBack() {
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            String text = et.getText().toString();
            File file = new File(ConstValue.get_title_File());
            getActionBar().setTitle(text);
            writeString(text, file);
            retunBack();
        }
    }

    private void writeString(String text, File file) {
        if (!file.exists()) {
            Toast.makeText(WriteTitleActivity.this, "文件不存在，请创建文件：" + ConstValue.get_title_File(), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(text);
            writer.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(WriteTitleActivity.this, "找不到文件error", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(WriteTitleActivity.this, "写文件error!", Toast.LENGTH_LONG).show();
        }
    }
}
