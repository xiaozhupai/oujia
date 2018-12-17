package com.example.administrator.oujiademo.statusBar;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oujiademo.R;
import com.example.administrator.oujiademo.listener.BatteryResultListener;
import com.example.administrator.oujiademo.listener.OnVolumeReceiverResultListener;
import com.example.administrator.oujiademo.util.BatteryReceiverUtil;
import com.example.administrator.oujiademo.util.DrawableUtil;
import com.example.administrator.oujiademo.util.VolumeReceiverUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "StatusActivity";
    private TextView textView;

    private static final int DBM_1 = -85;
    private static final int DBM_2 = -95;
    private static final int DBM_3 = -105;
    private static final int DBM_4 = -115;
    private static final int DBM_5 = -140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        init();
    }

    private void init() {
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        textView = findViewById(R.id.tv_content);

        Button btWifi = findViewById(R.id.bt_wifi);
        btWifi.setOnClickListener(this);

        Button btBlueTooth = findViewById(R.id.bt_bluetooth);
        btBlueTooth.setOnClickListener(this);

        Button btElectricity = findViewById(R.id.bt_electricity);
        btElectricity.setOnClickListener(this);

        Button btVolume = findViewById(R.id.bt_volume);
        btVolume.setOnClickListener(this);

        Button btImg = findViewById(R.id.bt_img);
        btImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                get4GNetDBM(this);
                break;
            case R.id.bt_wifi:
                initWifi();
                break;
            case R.id.bt_bluetooth:
                initBluetooth();
                break;
            case R.id.bt_electricity:
                initElectricity();
                break;
            case R.id.bt_volume:
                onVolume();
                getCurrentVolume();
                break;
            case R.id.bt_img:
//                getImgDrawable();
                onTest();
                break;
        }
    }

    private void getImgDrawable() {
        ImageView imageView = findViewById(R.id.iv);
        List<Integer> blinkList = new ArrayList<>();
        Resources res = getResources();
        Field[] field = R.drawable.class.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            field[i].setAccessible(true);
            int resId = res.getIdentifier(field[i].getName(), "drawable", getPackageName());
            Log.d("getImgDrawable", "\n" + "" + i + " " + " resId=" + resId + " " + field[i].getName());
            String name = field[i].getName();
            if (name.contains("blink_")) {
                blinkList.add(resId);
            }
        }
        Log.d("blinkList", "getImgDrawable: blink=" + blinkList.toString());
        if (0 != blinkList.size()) {
//            imageView.setImageResource(blinkList.get(0));
            AnimationDrawable animationDrawable = new AnimationDrawable();
            for (int resId : blinkList) {
                Drawable drawable = res.getDrawable(resId);
                animationDrawable.addFrame(drawable, 200);
            }
            animationDrawable.setOneShot(false);
            imageView.setImageDrawable(animationDrawable);
            animationDrawable.start();
        }
    }

    private void onTest() {
        ImageView imageView = findViewById(R.id.iv);
        List<Drawable> drawableList;
        drawableList = new DrawableUtil(this).getDrawable("blink_");
        AnimationDrawable animationDrawable = new AnimationDrawable();
        if (0 != drawableList.size()) {
            for (Drawable drawable : drawableList) {
                animationDrawable.addFrame(drawable, 300);
            }
            animationDrawable.setOneShot(false);
            imageView.setImageDrawable(animationDrawable);
            animationDrawable.start();
        }
    }

    private VolumeReceiverUtil volumeReceiverUtil;

    private void onVolume() {
        Log.d(TAG, "onVolume: onclick");
        volumeReceiverUtil = new VolumeReceiverUtil(this);
        volumeReceiverUtil.setOnVolumeReceiverResultListener(new OnVolumeReceiverResultListener() {
            @Override
            public void onCurrentVolume(int volume) {
                Log.d(TAG, "onCurrentVolume: 当前音量=" + volume);
            }
        });
        volumeReceiverUtil.register();
    }

    private void getCurrentVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.d(TAG, "音量：" + currentVolume);
        Log.d(TAG, "onReceive: 最大音量=" + mMaxVolume);
        int currentPercent = currentVolume * 100 / mMaxVolume;
        Log.d(TAG, "onReceive: 百分比=" + currentPercent);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//            // 音量+键
//            Log.d(TAG, "onKeyDown: 音量加");
//        }
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//            // 音量-键
//            Log.d(TAG, "onKeyDown: 音量减");
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    private BatteryReceiverUtil batteryReceiverUtil;

    /**
     * 电量状态
     */
    private void initElectricity() {
        batteryReceiverUtil = new BatteryReceiverUtil(this);
        batteryReceiverUtil.setBatteryResultListener(new BatteryResultListener() {
            @Override
            public void onResult(int levelPercent, int batteryState) {
                Log.d(TAG, "onResult: levelPercent=" + levelPercent + "  batteryState=" + batteryState);
                Toast.makeText(StatusActivity.this, " levelPercent=" + levelPercent + " batteryState=" + batteryState, Toast.LENGTH_SHORT).show();
            }
        });
        batteryReceiverUtil.register();

    }

    private BlueToothReceiver blueToothReceiver;

    /**
     * 蓝牙状态
     */
    private void initBluetooth() {
        Log.d(TAG, "initBluetooth: onclick");
        blueToothReceiver = new BlueToothReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(blueToothReceiver, intentFilter);

        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.d("BlueToothReceiver", "initBluetooth: 当前设备不支持使用蓝牙");
        } else {
            if (defaultAdapter.isEnabled()) {
                Log.d("BlueToothReceiver", "initBluetooth: 蓝牙已打开");
            } else {
                Log.d("BlueToothReceiver", "initBluetooth: 蓝牙已关闭");
            }
        }
    }

    private WifiChangeReceiver wifiChangeReceiver;

    /**
     * WiFi状态
     */
    private void initWifi() {
        Toast.makeText(this, "initWifi", Toast.LENGTH_SHORT).show();
        wifiChangeReceiver = new WifiChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(wifiChangeReceiver, filter);
    }

    /**
     * 得到当前手机4G信号强度值dbm
     */
    public void get4GNetDBM(Context context) {
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                //通过反射获取当前信号值
                Method method;
                try {
                    method = signalStrength.getClass().getMethod("getDbm");
                    int dbm = (int) method.invoke(signalStrength);
                    Log.d(TAG, "4G-dbm: " + dbm);
                    set4GNet(dbm);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onSignalStrengthsChanged: 获取4G信号强度值失败");
                }
            }
        };
        //开始监听
        if (tm != null) {
            tm.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        } else {
            Log.d(TAG, "get4GNetDBM: TelephonyManager为空，获取手机状态信息失败，无法开启监听");
        }
    }

    /**
     * 1、当信号大于等于 - 85d Bm时候，信号显示满格
     * 2、当信号大于等于 - 95d Bm时候，而小于 - 85d Bm时，信号显示3格
     * 3、当信号大于等于 - 105d Bm时候，而小于 - 95d Bm时，信号显示2格，不好捕捉到。
     * 4、当信号大于等于 - 115d Bm时候，而小于 - 105d Bm时，信号显示1格，不好捕捉到。
     * 5、当信号大于等于 - 140d Bm时候，而小于 - 115d Bm时，信号显示0格，不好捕捉到。
     *
     * @param dbm
     */
    private void set4GNet(int dbm) {
        if (dbm > DBM_1) {
            setLevel(5);
        } else if (DBM_2 < dbm && dbm < DBM_1) {
            setLevel(4);
        } else if (DBM_3 < dbm && dbm < DBM_2) {
            setLevel(3);
        } else if (DBM_4 < dbm && dbm < DBM_3) {
            setLevel(2);
        } else if (DBM_5 < dbm && dbm < DBM_4) {
            setLevel(1);
        }
    }

    /**
     * 当前信号显示格子数
     *
     * @param level
     */
    private void setLevel(int level) {
        switch (level) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != wifiChangeReceiver) {
            unregisterReceiver(wifiChangeReceiver);
        }
        if (null != blueToothReceiver) {
            unregisterReceiver(blueToothReceiver);
        }
        if (null != batteryReceiverUtil) {
            batteryReceiverUtil.unRegister();
        }
        if (null != volumeReceiverUtil) {
            volumeReceiverUtil.unRegister();
        }
    }


}
