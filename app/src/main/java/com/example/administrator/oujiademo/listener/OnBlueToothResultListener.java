package com.example.administrator.oujiademo.listener;

import android.bluetooth.BluetoothAdapter;

public interface OnBlueToothResultListener {

    /**
     * 蓝牙开启
     */
    int BLUETOOTH_ON = BluetoothAdapter.STATE_ON;

    /**
     * 蓝牙正在开启
     */
    int BLUETOOTH_TURNING_ON = BluetoothAdapter.STATE_TURNING_ON;

    /**
     * 蓝牙正在关闭
     */
    int BLUETOOTH_TURNING_OFF = BluetoothAdapter.STATE_TURNING_OFF;

    /**
     * 蓝牙关闭
     */
    int BLUETOOTH_OFF = BluetoothAdapter.STATE_OFF;

    void onBlueToothState(int state);
}
