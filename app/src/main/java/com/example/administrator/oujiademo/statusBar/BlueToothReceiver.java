package com.example.administrator.oujiademo.statusBar;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BlueToothReceiver extends BroadcastReceiver {
    String TAG = "BlueToothReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
            if (BluetoothAdapter.STATE_ON == state) {
                Log.d(TAG, "onReceive: 蓝牙已开启");
            } else if (BluetoothAdapter.STATE_TURNING_ON == state) {
                Log.d(TAG, "onReceive: 蓝牙正在开启");
            } else if (BluetoothAdapter.STATE_OFF == state) {
                Log.d(TAG, "onReceive: 蓝牙已关闭");
            } else if (BluetoothAdapter.STATE_TURNING_OFF == state) {
                Log.d(TAG, "onReceive: 蓝牙正在关闭");
            }
        }
    }
}
