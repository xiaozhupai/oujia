package com.example.administrator.oujiademo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.oujiademo.R;
import com.example.administrator.oujiademo.bean.User;
import com.example.administrator.oujiademo.greendao.DBManager;

import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {
    public static final String TAG = "GreenDaoActivity ";
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
//        init();
    }

    private void init() {
        dbManager = DBManager.getInstance(this);
//        dbManager.insertUser(new User(1L, "oo", 15));
//        dbManager.insertUser(new User(2L, "tt", 25));
        for (int i = 0; i < 15; i++) {
            dbManager.insertUser(new User(i + "", i));
        }
        List<User> users = dbManager.queryUserList();
        Log.d(TAG, "=======init: users=" + users.size());
        for (User user : users) {
            Log.d(TAG, "------ id=" + user.getId() + " name=" + user.getName() + " age=" + user.getAge());

        }
    }

    private void thread(Callback callback){
//        new Thread()
    }

    interface Callback{
        void call();
    }
}
