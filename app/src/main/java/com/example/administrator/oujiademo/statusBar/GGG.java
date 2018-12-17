package com.example.administrator.oujiademo.statusBar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class GGG {
    private static final List<OnReceivedBroadcast> mlist=new ArrayList<>();
    private static BroadcastReceiver mBoracddd;
    private Context mConxtext;

    public  interface OnReceivedBroadcast{
        void onR(Context context, Intent intent);
    }

//    public void add(OnReceivedBroadcast ggg){
//         if (mlist.add(ggg)){
//             //判断广播是否已注册
//                 if (null==mBoracddd){
//                       mConxtext.registerReceiver(mBoracddd,);
//                 }
//          }
//    }
//
//    public void revmoe(OnReceivedBroadcast ggg){
//        if (mlist.remove(ggg)){
//            //判断广播 mlist.size=0
//
//        }
//
//
//        public void unregiste(){
//            if (mBoracddd!=null){
//                        mBoracddd=null;
//            }
//        }

}
