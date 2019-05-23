package com.example.administrator.oujiademo.util;

import android.content.Context;
import android.util.Log;

import com.turing.authority.authentication.AuthenticationListener;
import com.turing.authority.authentication.SdkInitializer;
import com.turing.tts.TTSInitListener;
import com.turing.tts.TTSListener;
import com.turing.tts.TTSManager;

public class TulingUtils {
    public static final String TAG = "TulingUtils";
    public Context context;
    public static final String APPKEY = "3739afb1cbab44f7a2737ed2aee6282b";
    public static final String SECRET = "fVjK6851z3b3B1Nw";

    final String content="噫吁嚱，危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫，开国何茫然！尔来四万八千岁，不与秦塞通人烟。" +
            "西当太白有鸟道，可以横绝峨眉巅。地崩山摧壮士死，然后天梯石栈相钩连。上有六龙回日之高标，" +
            "下有冲波逆折之回川。黄鹤之飞尚不得过，猿猱欲度愁攀援。青泥何盘盘，百步九折萦岩峦。扪参历井仰胁息，以手抚膺坐长叹.";

    public TulingUtils(Context context) {
        this.context = context;
    }

    public void init() {
        Log.d(TAG, "===============init: ");
        SdkInitializer.init(context, APPKEY, SECRET, authenticationListener);
    }

    public AuthenticationListener authenticationListener = new AuthenticationListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "=============authenticationListener success");
            TTSManager.getInstance().init(context, new TTSInitListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "=============TTS init success");
                    TTSManager.getInstance().init(context, ttsInitListener);
                    TTSManager.getInstance().setSpeed(6);//设置语速
                    TTSManager.getInstance().setVolume(5);//设置音量
                    TTSManager.getInstance().setEnhancerTargetGain(1500);//增强音量数值设置,只支持在线,5000最大 1500最小 默认1500
                    TTSManager.getInstance().setEffect(TTSManager.BASE_EFFECT);//设置音效
                    TTSManager.getInstance().setEnhancerEnable(true);//增强音效开关，TTS增强音效暂只支持在线
//                    onStart(content);
                }

                @Override
                public void onFailed(int i, String s) {
                    Log.d(TAG, "TTS init failed errorCode=" + i + "   errorMsg=" + s);
                }
            });
        }

        @Override
        public void onError(final int errorCode, final String s) {
            Log.e(TAG, "errorCode=" + errorCode + "   errorMsg=" + s);
        }
    };


    public TTSInitListener ttsInitListener = new TTSInitListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "ttsInitListener onSuccess.");
        }

        @Override
        public void onFailed(int errorCode, String errorInfo) {
            Log.d(TAG, "ttsInitListener onFailed.");
        }
    };


    public TTSListener ttsListener = new TTSListener() {
        @Override
        public void onSpeakBegin(String content) {
            Log.d(TAG, "开始朗读 ");
        }

        @Override
        public void onSpeakPaused() {
            Log.d(TAG, "暂停朗读 ");
        }

        @Override
        public void onSpeakResumed() {
            Log.d(TAG, "重新朗读 ");
        }

        @Override
        public void onSpeakCompleted() {
            Log.d(TAG, "朗读结束 ");
        }

        @Override
        public void onSpeakFailed() {
            Log.d(TAG, "合成失败 ");

        }
    };

    public void onStart(String content) {
        TTSManager.getInstance().startTTS(content, TTSManager.ZHIWA_TONE, ttsListener);
    }

    public void onStop() {
        TTSManager.getInstance().stopTTS();
    }


}
