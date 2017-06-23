package com.xiaobailong.base;

import android.app.Application;

/**
 * Created by dongyuangui on 2017/5/31.
 */

public class BaseApplication extends Application {
    public static BaseApplication app = null;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
