package com.example.administrator.oujiademo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.oujiademo.activity.CoordinatorLayoutActivity;
import com.example.administrator.oujiademo.activity.DialogActivity;
import com.example.administrator.oujiademo.activity.DispatchTouchEventActivity;
import com.example.administrator.oujiademo.activity.WifiActivity;
import com.example.administrator.oujiademo.statusBar.StatusActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "touch";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
//                startActivity(new Intent(this, StatusActivity.class));
//                startActivity(new Intent(this, CoordinatorLayoutActivity.class));
//                startActivity(new Intent(this, DialogActivity.class));
//                skip(DispatchTouchEventActivity.class);
                skip(WifiActivity.class);
                break;
        }
    }

    private void skip(Class clas) {
        if (null != clas) {
            Intent intent = new Intent(this, clas);
            startActivity(intent);
        }
    }

}
