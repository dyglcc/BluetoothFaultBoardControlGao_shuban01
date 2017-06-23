package com.xiaobailong.bluetooth;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaobailong.bluetoothfaultboardcontrol.MainActivity;
import com.xiaobailong.bluetoothfaultboardcontrol.MediaListAdapter;
import com.xiaobailong.bluetoothfaultboardcontrol.R;
import com.xiaobailong.tools.ConstValue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class MediaFileListDialog extends Dialog implements OnItemClickListener, View.OnClickListener {


    private Stack<String> paths = new Stack<String>();
    private Context context;
    private HashMap<String, String> type = new HashMap<String, String>() {
        {
            put(".3gp", "video/3gpp");
            put(".apk", "application/vnd.android.package-archive");
            put(".asf", "video/x-ms-asf");
            put(".avi", "video/x-msvideo");
            put(".bin", "application/octet-stream");
            put(".bmp", "image/bmp");
            put(".c", "text/plain");
            put(".class", "application/octet-stream");
            put(".conf", "text/plain");
            put(".cpp", "text/plain");
            put(".doc", "application/msword");
            put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            put(".xls", "application/vnd.ms-excel");
            put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            put(".exe", "application/octet-stream");
            put(".gif", "image/gif");
            put(".gtar", "application/x-gtar");
            put(".gz", "application/x-gzip");
            put(".h", "text/plain");
            put(".htm", "text/html");
            put(".html", "text/html");
            put(".jar", "application/java-archive");
            put(".java", "text/plain");
            put(".jpeg", "image/jpeg");
            put(".jpg", "image/jpeg");
            put(".js", "application/x-JavaScript");
            put(".log", "text/plain");
            put(".m3u", "audio/x-mpegurl");
            put(".m4a", "audio/mp4a-latm");
            put(".m4b", "audio/mp4a-latm");
            put(".m4p", "audio/mp4a-latm");
            put(".m4u", "video/vnd.mpegurl");
            put(".m4v", "video/x-m4v");
            put(".mov", "video/quicktime");
            put(".mp2", "audio/x-mpeg");
            put(".mp3", "audio/x-mpeg");
            put(".mp4", "video/mp4");
            put(".mpc", "application/vnd.mpohun.certificate");
            put(".mpe", "video/mpeg");
            put(".mpeg", "video/mpeg");
            put(".mpg", "video/mpeg");
            put(".mpg4", "video/mp4");
            put(".mpga", "audio/mpeg");
            put(".msg", "application/vnd.ms-outlook");
            put(".ogg", "audio/ogg");
            put(".pdf", "application/pdf");
            put(".png", "image/png");
            put(".pps", "application/vnd.ms-powerpoint");
            put(".ppt", "application/vnd.ms-powerpoint");
            put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
            put(".prop", "text/plain");
            put(".rc", "text/plain");
            put(".rmvb", "audio/x-pn-realaudio");
            put(".rtf", "application/rtf");
            put(".sh", "text/plain");
            put(".tar", "application/x-tar");
            put(".tgz", "application/x-compressed");
            put(".txt", "text/plain");
            put(".wav", "audio/x-wav");
            put(".wma", "audio/x-ms-wma");
            put(".wmv", "audio/x-ms-wmv");
            put(".wps", "application/vnd.ms-works");
            put(".xml", "text/plain");
            put(".z", "application/x-compress");
            put(".zip", "application/x-zip-compressed");
            put("", "*/*");
        }
    };
    private ArrayList<String> list;
    private MediaListAdapter listAdapter;
    ListView listView;
    public MediaFileListDialog(Context context) {
        super(context);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("SDCARD根目录/" + ConstValue.dir);

        File file = new File(ConstValue.get_DIR());
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_LONG).show();
            return;
        }
        list = MainActivity.getDirFilesDir(file);
        paths.push(ConstValue.get_DIR());
        this.context = context;
        setContentView(R.layout.media_file_list);
         listView= (ListView) findViewById(R.id.listView_media_file);
        listAdapter = new MediaListAdapter(context, list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);

        findViewById(R.id.Button_Media_file_Back).setOnClickListener(this);
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
            paths.push(path);
        } else {
            String ext = path.substring(path.lastIndexOf("."), path.length());
            String dtype;
            if (type.containsKey(ext)) {
                dtype = type.get(ext);
            } else {
                dtype = type.get("");
            }
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file), dtype);
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            }
            dissss();
        }

    }

    private void dissss() {
        dismiss();
        type.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_Media_file_Back:
                paths.pop();

                if (paths.isEmpty()) {
                    dissss();
                } else {
                    String path = paths.peek();
                    File file = new File(path);
                    if (!file.exists()) {
                        Toast.makeText(context, "文件不存在！", Toast.LENGTH_LONG).show();
                        break;
                    }
                    list.clear();
                    list = MainActivity.getDirFilesDir(file);
                    listAdapter = new MediaListAdapter(context, list);
                    listView.setAdapter(listAdapter);
                    listView.setOnItemClickListener(this);
//                    listAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.Button_Search_BluetoothDevices:
                dissss();
                break;
        }
    }

}
