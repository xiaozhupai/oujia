package com.example.administrator.oujiademo.statusBar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.oujiademo.R;

public class AnimActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        initView();
    }

    private void initView() {
        Button btStart = findViewById(R.id.bt_start);
        btStart.setOnClickListener(this);

        imageView=findViewById(R.id.iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                onBatteryAnim();
                break;
        }
    }

    private void onBatteryAnim() {

    }
}
