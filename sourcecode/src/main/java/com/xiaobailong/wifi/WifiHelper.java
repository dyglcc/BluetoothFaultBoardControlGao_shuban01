package com.xiaobailong.wifi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaobailong.bluetoothfaultboardcontrol.MainActivity;
import com.xiaobailong.bluetoothfaultboardcontrol.R;
import com.xiaobailong.tools.SpDataUtils;

import java.util.List;

/**
 * Created by dongyuangui on 2017/9/16.
 */

public class WifiHelper {

    public void showWifiList(final Context context) {
        final WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final List<ScanResult> scanResults = manager.getScanResults();

        filterScanList(scanResults);

        AlertDialog dialog1 = new AlertDialog.Builder(context).setTitle("附近热点").setAdapter(new wifiAdapter(scanResults, context),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取选择ssid
                        ScanResult result = scanResults.get(which);
                        //-----
                        WifiConfiguration config = createWifiConfig(result.SSID, manager);
                        connnectWifi(manager, config);
                        ((MainActivity) context).connectNetwork();
                        SpDataUtils.saveWIFI_SSID(result.SSID);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(context, "取消列表", Toast.LENGTH_SHORT).show();
            }
        }).create();
        dialog1.show();
    }

    private void filterScanList(List<ScanResult> scanResults) {

        for (int i = scanResults.size() - 1; i >= 0; i--) {
            ScanResult result = scanResults.get(i);
            if (!TextUtils.isEmpty(result.SSID)) {
                if (!result.SSID.startsWith("Doit")) {
                    scanResults.remove(i);
                }
            }
        }
    }

    public void connnectWifi(WifiManager manager, WifiConfiguration config) {
        int i = manager.addNetwork(config);
        manager.enableNetwork(i, true);
    }

    public WifiConfiguration createWifiConfig(String ssid, WifiManager manager) {

        WifiConfiguration configuration = null;
        configuration = isExist(ssid, manager);
        if (configuration == null) {
            configuration = new WifiConfiguration();
            configuration.allowedAuthAlgorithms.clear();
            configuration.allowedGroupCiphers.clear();
            configuration.allowedKeyManagement.clear();
            configuration.allowedPairwiseCiphers.clear();
            configuration.allowedProtocols.clear();
            configuration.SSID = ssid;
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        return configuration;


    }

    private WifiConfiguration isExist(String ssid, WifiManager manager) {

        List<WifiConfiguration> configuredNetworks = manager.getConfiguredNetworks();
        for (WifiConfiguration configuration : configuredNetworks) {
            if (configuration.SSID.equals(ssid)) {
                return configuration;
            }
        }
        return null;

    }

    class wifiAdapter extends BaseAdapter {
        private Context context;
        private List<ScanResult> resultList;

        public wifiAdapter(List<ScanResult> data, Context context) {
            resultList = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return resultList == null ? 0 : resultList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_dialog_wifi_list, null);
                vh.ssid = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.ssid.setText(resultList.get(position).SSID);
            return convertView;
        }
    }

    final class ViewHolder {
        TextView ssid;
    }

}
