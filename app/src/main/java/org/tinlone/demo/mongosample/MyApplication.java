package org.tinlone.demo.mongosample;

import android.app.Application;
import android.content.IntentFilter;

import com.umeng.message.PushAgent;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class MyApplication extends Application {
    public static final String ACTION = "org.tinlone.demo.mongosample.upush";
    private PushAgent mPushAgent;
    public String deviceToken = "default";
    private static MyApplication mApp;
    private String power = "manager";

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mPushAgent = PushAgent.getInstance(this);
        //注册广播接收者的实际意义是启用与Application生命周期并行的事件来接收推送
        this.registerReceiver(new MyUPushReceiver(mPushAgent), new IntentFilter(ACTION));
    }

    public static MyApplication getContext() {
        return mApp;
    }

    public void setDeviceToken(String token) {
        this.deviceToken = token;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getPower(){
        return power;
    }

    public PushAgent getPushAgent() {
        return mPushAgent;
    }
}
