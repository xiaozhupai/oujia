package com.example.administrator.oujiademo.listener;

public interface OnWifiResultListener {
    int ZERO=0;
    int ONE=1;
    int TWO=2;
    int THREE=3;
    int FOUR=4;

    /**
     * WiFi信号格数
     *
     * @param level 0 为断开WiFi  4 为满格信号
     */
    void onWifiLevel(int level);
}
