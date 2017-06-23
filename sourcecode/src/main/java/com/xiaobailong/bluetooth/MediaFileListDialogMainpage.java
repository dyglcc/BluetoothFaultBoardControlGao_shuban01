package com.xiaobailong.bluetooth;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaobailong.bluetoothfaultboardcontrol.MainActivity;
import com.xiaobailong.bluetoothfaultboardcontrol.MediaListAdapter;
import com.xiaobailong.bluetoothfaultboardcontrol.R;
import com.xiaobailong.tools.ConstValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.xiaobailong.bluetoothfaultboardcontrol.PointOfFailureThatDialog.savePath;

public class MediaFileListDialogMainpage extends Dialog
        implements AdapterView.OnItemClickListener, View.OnClickListener {


    //    private Stack<String> paths = new Stack<String>();
    private Context context;
    private ArrayList<String> list;
    private MediaListAdapter listAdapter;
    private ListView listView;
    private Button buttonCancel;

    public MediaFileListDialogMainpage(Context context) {
        super(context);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("存储卡上：" + "/" + ConstValue.dir_autoblue + "/");

        File file = new File(ConstValue.getFailureDir());
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_LONG).show();
            return;
        }
        list = getDirFilesDir(file);
//        paths.push(ConstValue.get_DIR());
        this.context = context;
        setContentView(R.layout.media_file_list);
        listView = (ListView) findViewById(R.id.listView_media_file);
        listAdapter = new MediaListAdapter(context, list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);

        buttonCancel = (Button) findViewById(R.id.Button_Media_file_Back);
        buttonCancel.setText("取消");
        buttonCancel.setOnClickListener(this);

    }

    public static ArrayList<String> getDirFilesDir(File file) {
        ArrayList<String> list = new ArrayList<String>();
        if (file == null) {
            return list;
        }
        for (File f : file.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".txt")) {
                list.add(f.getAbsolutePath());
            }
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        String path = (String) arg0.getAdapter().getItem(position);
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在！", Toast.LENGTH_LONG).show();
            return;
        }
        if (file.isDirectory()) {
            list.clear();
            list = MainActivity.getDirFilesDir(file);
//            listAdapter.notifyDataSetChanged();
            listAdapter = new MediaListAdapter(context, list);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(this);
//            paths.push(path);
        } else {
//            BaseApplication.app.descStrFile = file;
            ((MainActivity) context).setFileName(file);
//            Toast.makeText(context, "设置名称" + file.getName(), Toast.LENGTH_SHORT).show();
            List<FaultBean> datas = readFile(file);

            ((MainActivity) context).setValues(datas);

            dissss();
        }

    }


    private void dissss() {
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_Media_file_Back:
//                paths.pop();
                dissss();
//                if (paths.isEmpty()) {
//                    dissss();
//                } else {
//                    File file = new File(path);
//                    if (!file.exists()) {
//                        Toast.makeText(context, "文件不存在！", Toast.LENGTH_LONG).show();
//                        break;
//                    }
//                    list.clear();
//                    list = MainActivity.getDirFilesDir(file);
//                    listAdapter = new MediaListAdapter(context, list);
//                    listView.setAdapter(listAdapter);
//                    listView.setOnItemClickListener(this);
//                    listAdapter.notifyDataSetChanged();
//                }
                break;
            case R.id.Button_Search_BluetoothDevices:
                dissss();
                break;
        }
    }

//    private void inputData(File file) {
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
//            String line = null;
//            //int index=0;
//            while ((line = br.readLine()) != null) {
//                if (line.startsWith("$") == true) {
//
//
//                }
//            }
//            br.close();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private List<FaultBean> readFile(File file) {
        List<FaultBean> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
            String line = null;
            //int index=0;
            while ((line = br.readLine()) != null) {
                FaultBean fb = null;
                line = line.trim();
                // 断路
                if (line.startsWith("$")) {

                    fb = new FaultBean();
                    fb.setType(ConstValue.type_duanlu);
                    fb.setValue(line.substring(1));

                    // 虚接
                } else if (line.startsWith("#")) {
                    fb = new FaultBean();
                    fb.setType(ConstValue.type_xujie);
                    fb.setValue(line.substring(1));
                    // 短路
                } else if (line.startsWith("@")) {
                    fb = new FaultBean();
                    fb.setType(ConstValue.type_shortFault);
                    fb.setValue(line.substring(1));
                }
                if (fb != null) {
                    list.add(fb);
                }
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static File getFile() {
        File file = new File(savePath + "failures.txt");
        if (file.exists())
            return file;
        file = new File(savePath);
        if (file.exists())
            return null;
        new File(savePath).mkdirs();
        return null;
    }

}

