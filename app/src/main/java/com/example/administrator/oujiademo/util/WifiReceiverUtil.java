package com.example.administrator.oujiademo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.administrator.oujiademo.listener.OnWifiResultListener;
import com.example.administrator.oujiademo.statusBar.CommonReceiver;

import static android.content.Context.WIFI_SERVICE;

/**
 * WiFi信号强度变化监听工具类
 */
public class WifiReceiverUtil implements CommonReceiver {
    private static final String TAG = "WifiReceiverUtil";
    private WifiReceiverUtil wifiReceiverUtil;
    private static WifiReceiver wifiReceiver;
    private Context context;
    private OnWifiResultListener onWifiResultListener;

    public WifiReceiverUtil(Context context) {
        this.context = context;
    }

    public void setOnWifiResultListener(OnWifiResultListener onWifiResultListener) {
        this.onWifiResultListener = onWifiResultListener;
    }

    @Override
    public void register() {
        if (null == wifiReceiver) {
            wifiReceiver = new WifiReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
            context.registerReceiver(wifiReceiver, filter);
        }
    }

    @Override
    public void unRegister() {
        if (null != wifiReceiver) {
            context.unregisterReceiver(wifiReceiver);
            wifiReceiver = null;
        }
    }

    private class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null == onWifiResultListener) {
                return;
            }
            String action = intent.getAction();
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {//监听WiFi的打开与关闭，与WiFi网络是否可用无关
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
                if (wifiState == WifiManager.WIFI_STATE_ENABLED) {//WiFi已连接
                    Log.d(TAG, "onReceive: WiFi已连接");
                    getWifiInfo(context);
                } else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {//WiFi已断开
                    Log.d(TAG, "onReceive: WiFi已断开");
                    onWifiResultListener.onWifiLevel(OnWifiResultListener.ZERO);
                }
            } else if (WifiManager.RSSI_CHANGED_ACTION.equals(action)) {
                getWifiInfo(context);
//                onChangeWifiState(wifiInfo);
            }
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
            Log.d(TAG, "isWifiEnable: 网络不可用");
        }
    }

    /**
     * 获取WiFi信息
     *
     * @param context
     * @return
     */
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
            onWifiResultListener.onWifiLevel(strength);
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
}
