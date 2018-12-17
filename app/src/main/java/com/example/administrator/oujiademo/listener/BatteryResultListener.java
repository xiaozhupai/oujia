package com.example.administrator.oujiademo.listener;

import android.os.BatteryManager;

public interface BatteryResultListener {

    /**
     * 充电中
     */
    int BATTERY_ON = BatteryManager.BATTERY_STATUS_CHARGING;

    /**
     * 充电完成
     */
    int BATTERY_FULL = BatteryManager.BATTERY_STATUS_FULL;

    /**
     * 未充电
     */
//    int BATTERY_OFF = BatteryManager.BATTERY_STATUS_NOT_CHARGING;

    /**
     * 未充电
     */
    int BATTERY_OFF = BatteryManager.BATTERY_STATUS_DISCHARGING;


    /**
     * @param levelPercent 当前剩余电量百分比
     * @param batteryState 充电状态
     */
    void onResult(int levelPercent, int batteryState);
}
