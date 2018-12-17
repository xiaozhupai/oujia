package com.example.administrator.oujiademo.statusBar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.WIFI_SERVICE;

public class WifiChangeReceiver extends BroadcastReceiver {
    String TAG = "WifiChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {//监听WiFi的打开与关闭，与WiFi网络是否可用无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
            if (wifiState == WifiManager.WIFI_STATE_ENABLED) {//WiFi已连接
                Log.d(TAG, "onReceive: WiFi已连接");
                getWifiInfo(context);
            } else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {//WiFi已断开
                setImage(0);
                Log.d(TAG, "onReceive: WiFi已断开");
            }
        } else if (WifiManager.RSSI_CHANGED_ACTION.equals(action)) {
            int wifiInfo = getWifiInfo(context);
            Toast.makeText(context, "wifi=" + wifiInfo, Toast.LENGTH_SHORT).show();
            onChangeWifiState(wifiInfo);
        }
    }

    /**
     * 判断WiFi是否可用
     *
     * @param context
     */
    private void isWifiEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            getWifiInfo(context);
        } else {
            setImage(0);
        }
    }

    private int getWifiInfo(Context context) {
        // Wifi的连接速度及信号强度：
        int strength = 0;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo info = wifiManager.getConnectionInfo();
        if (info.getBSSID() != null) {
            // 链接信号强度，5为获取的信号强度值在5以内
            strength = WifiManager.calculateSignalLevel(info.getRssi(), 5);
            // 链接速度
            int speed = info.getLinkSpeed();
            // 链接速度单位
            String units = WifiInfo.LINK_SPEED_UNITS;
            // Wifi名称
            String ssid = info.getSSID();
            Log.d("WifiChangeReceiver", "getWifiInfo: speed=" + speed);
            Log.d("WifiChangeReceiver", "getWifiInfo: units=" + units);
            Log.d("WifiChangeReceiver", "getWifiInfo: ssid=" + ssid);
            Log.d("WifiChangeReceiver", "getWifiInfo: strength=" + strength);
        }
        return strength;
    }

    /**
     * 0 为断开WiFi  4 为满格信号
     *
     * @param strength 0 - 4
     */
    private void onChangeWifiState(int strength) {
        switch (strength) {
            case 0://wifi断开

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4://信号最强

                break;
        }
    }

    private void setImage(int drawable) {

    }

}
