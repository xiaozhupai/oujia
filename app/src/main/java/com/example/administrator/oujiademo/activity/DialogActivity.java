package com.example.administrator.oujiademo.activity;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oujiademo.R;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
    public static final String TAG = "DialogActivity";
    /**
     * 普通弹框dialog
     */
    private Button mBtShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initView();
    }

    private void initView() {
        mBtShow = (Button) findViewById(R.id.bt_show);
        mBtShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_show:
                initSensor();
//                showDialog();
                break;
        }
    }

    private void showDialog() {
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, null);
        TextView cancel = layout.findViewById(R.id.tv_cancel);
        TextView confirm = layout.findViewById(R.id.tv_confirm);

        final Dialog dialog = new Dialog(this, R.style.customer_dialog);
        dialog.setContentView(layout);
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager windowManager = window.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DialogActivity.this, "确定", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


    private List<Float> list_x = new ArrayList<>();
    private List<Float> list_y = new ArrayList<>();
    private List<Float> list_z = new ArrayList<>();

    private boolean isFirst = true;

    /**
     * 传感器事件值改变时的回调接口：执行此方法的频率与注册传感器时的频率有关
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 大部分传感器会返回三个轴方向x,y,z的event值，值的意义因传感器而异
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        // 利用获得的三个float传感器值做些操作
//        Log.d(TAG, "onSensorChanged: list_x.size()=" + list_x.size());
        if (list_x.size() <= 30) {
            Log.d(TAG, "x = " + x + " y = " + y + "  z = " + z);
            list_x.add(x);
            list_y.add(y);
            list_z.add(z);
        } else {
            if (isFirst) {
                isFirst = false;
                onData();
            }
        }
    }

    /**
     * 传感器精度发生改变的回调接口
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //在传感器精度发生改变时做些操作，accuracy为当前传感器精度
        Log.d(TAG, "=============================================accuracy=" + accuracy);
    }

    private SensorManager sensorManager;
    private Sensor sensor;

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (null != sensorManager) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);

            if (null != sensor) {
                // Success! There's a magnetometer.
                Log.d(TAG, "============================================== sensor success");
                boolean b = sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                Log.d(TAG, "===================================================================b=" + b);
            } else {
                // Failure! No magnetometer.
                Log.d(TAG, "============================================== sensor fail");
            }
        } else {
            Log.d(TAG, "==================================== sensorManager is empty");
        }
    }

    private void onData() {
        float maxInde_x = 0;//定义最大值为该数组的第一个数
        float minInde_x = 0;//定义最小值为该数组的第一个数

        float maxInde_y = 0;//定义最大值为该数组的第一个数
        float minInde_y = 0;//定义最小值为该数组的第一个数

        float maxInde_z = 9.8f;//定义最大值为该数组的第一个数
        float minInde_z = 9.8f;//定义最小值为该数组的第一个数

        for (int i = 0; i < list_x.size(); i++) {
            if (maxInde_x < list_x.get(i)) {
                maxInde_x = list_x.get(i);
            }
            if (minInde_x > list_x.get(i)) {
                minInde_x = list_x.get(i);
            }
        }
        for (int i = 0; i < list_y.size(); i++) {
            if (maxInde_y < list_y.get(i)) {
                maxInde_y = list_y.get(i);
            }
            if (minInde_y > list_y.get(i)) {
                minInde_y = list_y.get(i);
            }
        }

        for (int i = 0; i < list_z.size(); i++) {
            if (maxInde_z < list_z.get(i)) {
                maxInde_z = list_z.get(i);
            }
            if (minInde_z > list_z.get(i)) {
                minInde_z = list_z.get(i);
            }
        }

        Log.d(TAG, "这个数组的最大值为  x ：" + maxInde_x + " 最小值为：" + minInde_x);
        Log.d(TAG, "这个数组的最大值为  y ：" + maxInde_y + " 最小值为：" + minInde_y);
        Log.d(TAG, "这个数组的最大值为  z :" + maxInde_z + " 最小值为：" + minInde_z);
        Log.d(TAG, "onData: ---------------------------");
        Log.d(TAG, "差值  x :" + (maxInde_x - minInde_x));
        Log.d(TAG, "差值  y :" + (maxInde_y - minInde_y));
        Log.d(TAG, "差值  z :" + (maxInde_z - minInde_z));

        //不动
//        这个数组的最大值为  x ：0.0 最小值为：-0.086
//        这个数组的最大值为  y ：0.0 最小值为：-0.158
//        这个数组的最大值为  z :9.8 最小值为：9.704
//        onData: ---------------------------
//        差值  x :0.086
//        差值  y :0.158
//        差值  z :0.09599972


        //左右
//        这个数组的最大值为  x ：8.138 最小值为：-7.01
//        这个数组的最大值为  y ：0.0 最小值为：-3.95
//        这个数组的最大值为  z :10.573 最小值为：7.872
//        onData: ---------------------------
//        差值  x :15.148
//        差值  y :3.95
//        差值  z :2.7009997


        //上下
//        这个数组的最大值为  x ：1.336 最小值为：-1.364
//        这个数组的最大值为  y ：1.343 最小值为：-6.73
//        这个数组的最大值为  z :18.445 最小值为：0.682
//        onData: ---------------------------
//        差值  x :2.6999998
//        差值  y :8.073
//        差值  z :17.763


        //前后
//        这个数组的最大值为  x ：2.578 最小值为：-3.059
//        这个数组的最大值为  y ：7.542 最小值为：-9.459
//        这个数组的最大值为  z :11.327 最小值为：7.937
//        onData: ---------------------------
//        差值  x :5.637
//        差值  y :17.001
//        差值  z :3.3899999



    }


}
