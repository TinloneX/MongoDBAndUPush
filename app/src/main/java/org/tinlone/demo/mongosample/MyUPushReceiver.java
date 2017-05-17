package org.tinlone.demo.mongosample;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import static android.os.Looper.getMainLooper;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class MyUPushReceiver extends BroadcastReceiver {

    PushAgent mPushAgent;

    UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
        @Override
        public void dealWithCustomAction(Context context, UMessage msg) {
            Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
        }
    };

    UmengMessageHandler messageHandler = new UmengMessageHandler() {
        // 任何推送都会调用此方法
        @Override
        public Notification getNotification(Context context, UMessage msg) {
            TLog.i(msg);
            switch (msg.builder_id) {
                case 1:
                    return super.getNotification(context, msg);
                default:
                    //默认为0，若填写的builder_id并不存在，也使用默认。
                    return super.getNotification(context, msg);
            }
        }

        @Override
        public void dealWithCustomMessage(final Context context, final UMessage msg) {
            new Handler(getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
                    boolean isClickOrDismissed = true;
                    if (isClickOrDismissed) {
                        //自定义消息的点击统计
                        UTrack.getInstance(MyApplication.getContext()).trackMsgClick(msg);
                    } else {
                        //自定义消息的忽略统计
                        UTrack.getInstance(MyApplication.getContext()).trackMsgDismissed(msg);
                    }
                    Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    public MyUPushReceiver(PushAgent mPush) {
        this.mPushAgent = mPush;
        initUPush();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    private void initUPush() {
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                TLog.f("注册成功会返回device token:", deviceToken);
                MyApplication.getContext().deviceToken = deviceToken;
            }

            @Override
            public void onFailure(String s, String s1) {
                TLog.f(s, s1);
            }
        });
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        mPushAgent.setMessageHandler(messageHandler);
        //免打扰时段 23：00 ~ 7：00
        mPushAgent.setNoDisturbMode(23, 0, 7, 0);
        // #param 秒时间内多次收到推送只提示一次，只显示最新一次
        mPushAgent.setMuteDurationSeconds(60);
        //通知栏最多同时显示 #param 条推送
        mPushAgent.setDisplayNotificationNumber(2);
        // 由 #param 控制通知声音
        // 有三种状态：
        // MsgConstant.NOTIFICATIONPLAYSERVER（服务端控制）
        // MsgConstant.NOTIFICATIONPLAYSDKENABLE（客户端允许）
        // MsgConstant.NOTIFICATIONPLAYSDKDISABLE（客户端禁止）
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        // 由 #param 控制通知呼吸灯
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
        // 由 #param 控制通知振动
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动
        //应用前台时不显示通知，可在handler中拦截，使用应用内通知
        mPushAgent.setNotificaitonOnForeground(true);
        //为用户设置/添加标志，如区分职位
       UPushUtil.addTag(mPushAgent,MyApplication.getContext().getPower());
    }


}
