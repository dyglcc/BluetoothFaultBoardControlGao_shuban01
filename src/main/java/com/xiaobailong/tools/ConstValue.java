package com.xiaobailong.tools;

import android.os.Environment;

public class ConstValue {
    /**
     * 取sdcard的路径
     *
     * @return 如果Sdcard没有，或者不可写，返回null
     */

    public static String titleName = "标题名称.txt";
    public static String dir = "simulation";

    public static String getSdcardPath() {
        if (haveSdcard()) {
            return Environment.getExternalStorageDirectory()
                    .getPath();
        } else {
            return null;
        }
    }

    /**
     * 是否有SDCARD
     *
     * @return 有SDCARD, 返回true, 否则返回false
     */
    public static boolean haveSdcard() {
        return (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED));
    }

    public static String get_DIR() {
//        return getSdcardPath() ;
        return getSdcardPath() + "/" + dir + "/";
    }

    public static String get_title_File() {
        return getSdcardPath() + "/" + dir + "/" + titleName;
    }


}
