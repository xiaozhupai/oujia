package com.example.administrator.oujiademo.util;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;

import com.example.administrator.oujiademo.listener.OnVolumeReceiverResultListener;
import com.example.administrator.oujiademo.statusBar.CommonReceiver;

/**
 * 监听当前音量
 */
public class VolumeReceiverUtil implements CommonReceiver {
    private static final String TAG = "VolumeReceiverUtil";
    private Context context;
    private OnVolumeReceiverResultListener onVolumeReceiverResultListener;
    private SettingsContentObserver settingsContentObserver;

    public VolumeReceiverUtil(Context context) {
        this.context = context;
    }

    public void setOnVolumeReceiverResultListener(OnVolumeReceiverResultListener onVolumeReceiverResultListener) {
        this.onVolumeReceiverResultListener = onVolumeReceiverResultListener;
    }

    @Override
    public void register() {
        if (null == settingsContentObserver) {
            settingsContentObserver = new SettingsContentObserver(new Handler());
        }
        context.getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, settingsContentObserver);
    }

    @Override
    public void unRegister() {
        if (null != settingsContentObserver) {
            context.getContentResolver().unregisterContentObserver(settingsContentObserver);
            settingsContentObserver = null;
        }
    }

    public class SettingsContentObserver extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public SettingsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if (null == onVolumeReceiverResultListener) {
                return;
            }
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//            Log.d(TAG, "当前音量：" + currentVolume);
//            Log.d(TAG, "onReceive: 最大音量=" + mMaxVolume);
            int currentPercent = currentVolume * 100 / mMaxVolume;
            Log.d(TAG, "onReceive: 音量百分比=" + currentPercent);
            onVolumeReceiverResultListener.onCurrentVolume(currentPercent);
        }
    }
}
