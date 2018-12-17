package com.example.administrator.oujiademo.util;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.administrator.oujiademo.listener.OnBlueToothResultListener;
import com.example.administrator.oujiademo.statusBar.CommonReceiver;

/**
 * 监听蓝牙是否关闭
 */
public class BlueToothReceiverUtil implements CommonReceiver {
    private static BlueToothReceiver blueToothReceiver;
    private Context context;
    private OnBlueToothResultListener onBlueToothResultListener;

    public BlueToothReceiverUtil(Context context) {
        this.context = context;
    }

    public void setOnBlueToothResultListener(OnBlueToothResultListener onBlueToothResultListener) {
        this.onBlueToothResultListener = onBlueToothResultListener;
    }

    @Override
    public void register() {
        if (null == blueToothReceiver) {
            blueToothReceiver = new BlueToothReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            context.registerReceiver(blueToothReceiver, intentFilter);
        }
    }

    @Override
    public void unRegister() {
        if (null != blueToothReceiver) {
            context.unregisterReceiver(blueToothReceiver);
            blueToothReceiver = null;
        }
    }

    private class BlueToothReceiver extends BroadcastReceiver {
        String TAG = "BlueToothReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: 蓝牙广播");
            if (null == onBlueToothResultListener) {
                return;
            }
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
                if (BluetoothAdapter.STATE_ON == state) {
                    Log.d(TAG, "onReceive: 蓝牙已开启");
                    onBlueToothResultListener.onBlueToothState(BluetoothAdapter.STATE_ON);
                } else if (BluetoothAdapter.STATE_TURNING_ON == state) {
                    Log.d(TAG, "onReceive: 蓝牙正在开启");
                    onBlueToothResultListener.onBlueToothState(BluetoothAdapter.STATE_TURNING_ON);
                } else if (BluetoothAdapter.STATE_OFF == state) {
                    Log.d(TAG, "onReceive: 蓝牙已关闭");
                    onBlueToothResultListener.onBlueToothState(BluetoothAdapter.STATE_OFF);
                } else if (BluetoothAdapter.STATE_TURNING_OFF == state) {
                    Log.d(TAG, "onReceive: 蓝牙正在关闭");
                    onBlueToothResultListener.onBlueToothState(BluetoothAdapter.STATE_TURNING_OFF);
                }
            }
        }
    }

}
