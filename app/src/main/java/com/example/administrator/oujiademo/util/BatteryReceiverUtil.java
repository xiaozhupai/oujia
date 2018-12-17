package com.example.administrator.oujiademo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import com.example.administrator.oujiademo.listener.BatteryResultListener;
import com.example.administrator.oujiademo.statusBar.CommonReceiver;

public class BatteryReceiverUtil implements CommonReceiver {
    private static final String TAG = "BatteryReceiverUtil";
    private Context context;
    private static BatteryReceiver batteryReceiver;
    private BatteryResultListener batteryResultListener;


    public BatteryReceiverUtil(Context context) {
        this.context = context;
    }

    public void setBatteryResultListener(BatteryResultListener batteryResultListener) {
        this.batteryResultListener = batteryResultListener;
    }

    @Override
    public void register() {
        if (null == batteryReceiver) {
            batteryReceiver = new BatteryReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
            context.registerReceiver(batteryReceiver, intentFilter);
        }
    }

    @Override
    public void unRegister() {
        if (null != batteryReceiver) {
            context.unregisterReceiver(batteryReceiver);
            batteryReceiver = null;
        }
    }

    private class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null == batteryResultListener) {
                return;
            }
            //当前电量
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            //总电量
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int levelPercent = (int) (((float) level / scale) * 100);
            Log.d(TAG, "onReceive: 电量=" + levelPercent);

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    Log.d(TAG, "onReceive: 充电中");
                    batteryResultListener.onResult(levelPercent, BatteryResultListener.BATTERY_ON);
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    Log.d(TAG, "onReceive: 充电完成");
                    batteryResultListener.onResult(levelPercent, BatteryResultListener.BATTERY_FULL);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    Log.d(TAG, "onReceive: 未充电 " + BatteryManager.BATTERY_STATUS_DISCHARGING);
                    batteryResultListener.onResult(levelPercent, BatteryResultListener.BATTERY_OFF);
                    break;
            }
        }
    }
}
