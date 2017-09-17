package com.xiaobailong.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import com.xiaobailong.event.Msgtype;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by dongyuangui on 2017/9/16.
 */

public class WifiConnectReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {

            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                String typeName = networkInfo.getTypeName();
                if (!"WIFI".equals(typeName)) {
                    return;
                }
                Msgtype msgtype = null;
                switch (state) {
                    case DISCONNECTED:
                        msgtype = new Msgtype();
                        msgtype.setConnect(false);
                        EventBus.getDefault().post(msgtype);
                        break;
                    case CONNECTED:
                        msgtype = new Msgtype();
                        msgtype.setConnect(true);
                        EventBus.getDefault().post(msgtype);
                        break;
                    case CONNECTING:
                        break;
                    default:
                        break;
                }
            }
        }

    }
}
