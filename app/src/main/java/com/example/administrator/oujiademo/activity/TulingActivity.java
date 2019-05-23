package com.example.administrator.oujiademo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.example.administrator.oujiademo.R;
import com.example.administrator.oujiademo.util.TulingUtils;

import java.util.ArrayList;
import java.util.List;

public class TulingActivity extends Activity {
    public static final String TAG = "TulingActivity";
    private static final int PERMISSION_REQUESTCODE = 100;
    private PermissionListener mListener;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuling);
        handler = new Handler(getMainLooper());
        String[] strings = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.RECORD_AUDIO};
        requetPermission(strings, null);
    }

    public void onInit() {

    }

    public void onStart(View view) {
        Log.d(TAG, "onStart: ");
        if (null != tulingUtils)
            tulingUtils.onStart(content);
    }

    public void onStop(View view) {
        Log.d(TAG, "onStop: ");
        if (null != tulingUtils)
            tulingUtils.onStop();
    }



    public interface PermissionListener {
        void onSuccess();

        void onFail(List<String> deiedPermission);
    }

    public void requetPermission(String[] permissions, PermissionListener listener) {
//        this.mListener = listener;
        List<String> mList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mList.add(permission);
            }
        }

        if (!mList.isEmpty()) {
            ActivityCompat.requestPermissions(this, mList.toArray(new String[mList.size()]), PERMISSION_REQUESTCODE);
        } else {
//            mListener.onSuccess();
            init();
        }
    }

    final String content = "噫吁嚱，危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫，开国何茫然！尔来四万八千岁，不与秦塞通人烟。" +
            "西当太白有鸟道，可以横绝峨眉巅。地崩山摧壮士死，然后天梯石栈相钩连。上有六龙回日之高标，" +
            "下有冲波逆折之回川。黄鹤之飞尚不得过，猿猱欲度愁攀援。青泥何盘盘，百步九折萦岩峦。扪参历井仰胁息，以手抚膺坐长叹.";
    private TulingUtils tulingUtils;

    private void init() {
        tulingUtils = new TulingUtils(this);
        tulingUtils.init();

        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "----------------onStart--------------------");
                tulingUtils.onStart(content);
            }
        },3000);*/

        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "---------------onStop--------------- ");
                tulingUtils.onStop();
            }
        },10*1000);*/
    }


}
