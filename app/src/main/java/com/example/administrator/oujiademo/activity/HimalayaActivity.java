package com.example.administrator.oujiademo.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.oujiademo.R;
import com.fmxos.platform.FmxosPlatform;
import com.fmxos.platform.sdk.AlbumCore;
import com.fmxos.platform.sdk.XmlyAlbum;
import com.fmxos.platform.sdk.XmlyAudioUrl;
import com.fmxos.platform.sdk.XmlyPage;
import com.fmxos.platform.sdk.XmlyRequest;
import com.fmxos.platform.sdk.XmlyTrack;
import com.fmxos.platform.sdk.burl.GetBurl;
import com.google.gson.Gson;
import com.oujia.himalaya.HimalayaSdk;

import java.util.List;

public class HimalayaActivity extends AppCompatActivity {
    public static final String TAG = "HimalayaActivity";
    private XmlyRequest queryAlbumDetailRequest;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        init();
    }

    private void init() {
        Log.d(TAG, "init: ------===-------");
        HimalayaSdk himalayaSdk=new HimalayaSdk(getApplication());
        himalayaSdk.getBatchAudioUrl(new String[]{"5464842"}, new GetBurl.BatchAudioUrlCallback() {
            @Override
            public void onBatchAudioUrlSuccess(List<XmlyAudioUrl> list) {
                Log.d(TAG, "============list=" + list.size());
                if (list.size()>0){
                    XmlyAudioUrl xmlyAudioUrl = list.get(0);
                    Log.d(TAG, "============list=" + xmlyAudioUrl.getId());
                    Log.d(TAG, "=======yyy=====list=" + ((xmlyAudioUrl.getId() + "").equals("5464842")));
                    Log.d(TAG, "============list=" + xmlyAudioUrl.getPlayUrl());
                }
            }

            @Override
            public void onBatchAudioUrlFailure(String s) {
                Log.d(TAG, "==========fail=" + s);
            }
        });

        /*FmxosPlatform.init(getApplication());
        Log.d(TAG, "------getApplication-------");
        // 设置数据型，可重写详情页
//        FmxosPlatform.setSDKMode(FmxosPlatform.SDKMode.Data);
        Log.d(TAG, "------setSDKMode-------");
        getUrl();

        FmxosPlatform.setAlbumDetailCallback(new FmxosPlatform.AlbumDetailCallback() {
            @Override
            public void onAlbumDetailPageStart(Activity activity, AlbumCore albumCore) {
                Log.d(TAG, gson.toJson(albumCore));
                Log.d(TAG, "onAlbumDetailPageStart: -------------albumCore=" + gson.toJson(albumCore));
                getDetailRequest(albumCore);
            }
        });*/
    }

    private void getDetailRequest(final AlbumCore albumCore) {
        Log.d(TAG, "getDetailRequest: ------------");
        queryAlbumDetailRequest = FmxosPlatform.queryAlbumDetail(albumCore, new FmxosPlatform.AlbumDetailPage() {
            @Override
            public void onPageSuccess(final XmlyAlbum album, final List<XmlyTrack> tracks, final XmlyPage page) {
                Log.d(TAG, "onPageSuccess: ----------------");
                FmxosPlatform.getBatchAudioUrl(parseIds(tracks), album.isShouldPaid(), GetBurl.PlayDeviceType.Phone, null, "1144_00_10044", new GetBurl.BatchAudioUrlCallback() {
                    @Override
                    public void onBatchAudioUrlSuccess(List<XmlyAudioUrl> list) {
                        Log.d(TAG, "============list=" + list.size());
                        Log.d(TAG, "============list=" + list);
                        Log.v(TAG, gson.toJson(album) + gson.toJson(list) + tracks.size() + gson.toJson(page));
                    }

                    @Override
                    public void onBatchAudioUrlFailure(String message) {
                        Log.v(TAG, message);
                    }
                });
            }

            private String[] parseIds(List<XmlyTrack> tracks) {
                String[] ids = new String[tracks.size()];
                for (int i = 0; i < tracks.size(); i++) {
                    ids[i] = tracks.get(i).getId();
                }
                return ids;
            }

            @Override
            public void onPageFailure(String message) {
                Log.v(TAG, message);
            }
        });
    }

    private void getUrl() {
        FmxosPlatform.getBatchAudioUrl(new String[]{"5464842"}, false, GetBurl.PlayDeviceType.Phone, null, "1144_00_10044", new GetBurl.BatchAudioUrlCallback() {
            @Override
            public void onBatchAudioUrlSuccess(List<XmlyAudioUrl> list) {
                Log.d(TAG, "============list=" + list.size());
                if (list.size()>0){
                    XmlyAudioUrl xmlyAudioUrl = list.get(0);
                    Log.d(TAG, "============list=" + xmlyAudioUrl.getId());
                    Log.d(TAG, "============list=" + ((xmlyAudioUrl.getId() + "").equals("5464842")));
                    Log.d(TAG, "============list=" + xmlyAudioUrl.getPlayUrl());
                }
            }

            @Override
            public void onBatchAudioUrlFailure(String s) {
                Log.d(TAG, "==========fail=" + s);
            }
        });
    }


}
