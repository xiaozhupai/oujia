package com.example.administrator.oujiademo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.oujiademo.R;

public class DispatchTouchEventActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * onclick
     */
    private Button mOnclick;
    /**
     * ON
     */
    private Button mOn;
    /**
     * OFF
     */
    private Button mOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_touch_event);
        initView();

    }

    private void initView() {
        mOnclick.setOnClickListener(this);
        mOn = (Button) findViewById(R.id.on);
        mOn.setOnClickListener(this);
        mOff = (Button) findViewById(R.id.off);
        mOff.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.on:
                Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();

                break;
            case R.id.off:
                break;
        }
    }

    protected void writeAdbSetting(Context mContext, boolean enabled) {
        Settings.Global.putInt(mContext.getContentResolver(),
                Settings.Global.ADB_ENABLED, enabled ? 1 : 0);
        LocalBroadcastManager.getInstance(mContext)
                .sendBroadcast(new Intent(" com.android.settingslib.development.AbstractEnableAdbController.ENABLE_ADB_STATE_CHANGED"));
    }
}
