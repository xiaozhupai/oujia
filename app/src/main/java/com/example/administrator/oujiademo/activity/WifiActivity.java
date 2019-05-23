package com.example.administrator.oujiademo.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.example.administrator.oujiademo.R;
import com.example.administrator.oujiademo.bean.Student;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WifiActivity extends AppCompatActivity {
    public static final String TAG = "WifiActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
//        initWifi();
        onComparator();
    }

    private void initWifi() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
        if (null != wifiManager) {
            wifiManager.setWifiEnabled(true);
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (null != connectionInfo) {
                Log.d(TAG, "initWifi: 当前连接的wifi ssid= " + connectionInfo.getSSID() + " networkId=" + connectionInfo.getNetworkId());
            } else {
                Log.d(TAG, "initWifi: 当前未连接WiFi");
            }

            List<ScanResult> nearByWifi = findNearByWifi();
            if (null != nearByWifi) {
                ScanResult scanResult = nearByWifi.get(0);
                String ssid = "OJ";
                String password = "oujia888888!";

            }

            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            if (null != configuredNetworks) {
                for (WifiConfiguration w : configuredNetworks) {
                    Log.d(TAG, "initWifi:  ssid=" + w.SSID + "   networkId=" + w.networkId);
                }
            }
        }
    }

    private List<ScanResult> findNearByWifi() {
        Log.d(TAG, "start to findNearByWifi ");
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
        if (null != wifiManager) {
            List<ScanResult> scanResults = wifiManager.getScanResults();
            if (null != scanResults) {
                for (ScanResult s : scanResults) {
                    Log.d(TAG, "ssid= " + s.SSID + "  level= " + s.level);
                }
                return scanResults;
            } else {
                Log.d(TAG, "initWifi: scanResults为空");
            }
        }
        return null;
    }

    private void connect(String ssid, String password) {
        //连接指定的wifi
    }

    private void onComparator() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("h", 20));
        studentList.add(new Student("a", 10));
        studentList.add(new Student("c", 12));
        studentList.add(new Student("w", 18));

        Log.d(TAG, "studentList 1: " + studentList.toString());
        Collections.sort(studentList, comparator);
        Log.d(TAG, "studentList 2: " + studentList.toString());
    }

    private Comparator<Student> comparator = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2) {
            String name1 = o1.getName();
            String name2 = o2.getName();
            Log.d(TAG, "compare: name1: " + name1 + " name2: " + name2);

            int age1 = o1.getAge();
            int age2 = o2.getAge();
            Log.d(TAG, "compare: age1: " + age1 + " age2: " + age2);
            return null != name1 && null != name2 ? name1.compareTo(name2) : 0;
//            return (age1-age2);
        }
    };


}
