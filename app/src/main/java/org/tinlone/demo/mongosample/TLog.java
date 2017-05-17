package org.tinlone.demo.mongosample;

import android.util.Log;

/**
 * Created by zhaojinlong on 2016/12/20 0020.
 */

public class TLog {

    public static String TAG = "zjl";
    private static boolean showLog = BuildConfig.DEBUG;

    public static void showLog(boolean showLog) {
        TLog.showLog = showLog;
    }

    public static void d(Object object) {
        if (showLog) {
            Log.d(TAG, logValueOf(object));
        } else return;
    }

    public static void d(String tag, Object object) {
        if (showLog) {
            Log.d(tag, logValueOf(object));
        } else return;
    }

    public static void i(Object object) {
        if (showLog) {
            Log.i(TAG, logValueOf(object));
        } else return;
    }

    public static void i(String tag, Object object) {
        if (showLog) {
            Log.i(tag, logValueOf(object));
        } else return;
    }

    public static void i(String msg, Object... objs) {
        if (showLog) {
            Log.i(TAG, logValueOf(msg, objs));
        } else return;
    }

    public static void e(Object object) {
        if (showLog) {
            Log.e(TAG, logValueOf(object));
        } else return;
    }

    public static void e(String msg, Object... objs) {
        if (showLog) {
            Log.e(TAG, logValueOf(msg, objs));
        } else return;
    }

    public static void e(String tag, Object object) {
        if (showLog) {
            Log.e(tag, logValueOf(object));
        } else return;
    }

    public static void w(Object object) {
        if (showLog) {
            Log.w(TAG, logValueOf(object));
        } else return;
    }

    public static void w(String tag, Object object) {
        if (showLog) {
            Log.w(tag, logValueOf(object));
        } else return;
    }

    public static void f(Object object) {
        Log.e("#-----FOCUS-HERE-----#", logValueOf(object));
    }

    public static void f(String key, Object object) {
        Log.e("#-----FOCUS-HERE-----#", key + logValueOf(object));
    }

    public static void setTag(String tag) {
        TAG = tag;
    }

    private static String logValueOf(Object object) {
        return object == null ? "null" : object.toString();
    }

    private static String logValueOf(String msg, Object... objs) {
        try {
            return String.format(msg, objs);
        } catch (Exception e) {
            e.printStackTrace();
            return "---WARNING--- 占位符对应格式有误:" + msg;
        }
    }


}
