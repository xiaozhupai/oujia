package com.example.administrator.oujiademo.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oujiademo.R;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

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
                showDialog();
                break;
        }
    }

    private void showDialog() {
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, null);
        TextView cancel = layout.findViewById(R.id.tv_cancel);
        TextView confirm = layout.findViewById(R.id.tv_confirm);

        final Dialog dialog = new Dialog(this,R.style.customer_dialog);
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
}
