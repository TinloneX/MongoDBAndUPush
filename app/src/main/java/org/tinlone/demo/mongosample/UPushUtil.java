package org.tinlone.demo.mongosample;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import com.umeng.message.tag.TagManager;

import java.util.List;
import java.util.Map;

/**
 * Umeng 推送工具
 */
public class UPushUtil {

    /**
     * 将参数放入Intent的代码
     *
     * @param intent
     * @param msg
     * @return
     */
    public Intent addMessageToIntent(Intent intent, UMessage msg) {
        if (intent == null || msg == null || msg.extra == null)
            return intent;

        for (Map.Entry<String, String> entry : msg.extra.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != null)
                intent.putExtra(key, value);
        }
        return intent;
    }

    /**
     * 当资源包名和应用程序包名不一致时，调用设置资源包名的接口
     *
     * @param mPushAgent
     * @param packageName
     */
    public static void setSourcePath(PushAgent mPushAgent, String packageName) {
        mPushAgent.setResourcePackageName(packageName);
    }

    /**
     * 在原有标签基础上增加新标签。
     * 设置用户标签(tag)
     */
    public static void addTag(PushAgent mPushAgent, String... tags) {
        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                //isSuccess表示操作是否成功
            }
        }, tags);
    }

    /**
     * 将之前添加的标签中的一个或多个删除。
     */
    public static void deleteTag(PushAgent mPushAgent, String... tags) {
        mPushAgent.getTagManager().delete(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {

            }
        }, tags);
    }

    /**
     * 删除之前添加的所有标签，重置为新标签。
     */
    public static void updateTag(PushAgent mPushAgent) {
        mPushAgent.getTagManager().update(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {

            }
        }, MyApplication.getContext().getPower());
    }

    /**
     * 删除之前添加的所有标签。
     */
    public static void resetTag(PushAgent mPushAgent) {
        mPushAgent.getTagManager().reset(new TagManager.TCallBack() {
            @Override
            public void onMessage(boolean isSuccess, ITagManager.Result result) {

            }
        });
    }

    /**
     * 获取服务器端的所有标签
     */
    public static void getTag(PushAgent mPushAgent) {
        mPushAgent.getTagManager().list(new TagManager.TagListCallBack() {
            @Override
            public void onMessage(boolean isSuccess, List<String> result) {

            }
        });
    }

    public static void addAlias(PushAgent mPushAgent, String alias, String type) {
        mPushAgent.addAlias(alias, type, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {

            }
        });
    }

    public static void addExclusiveAlias(PushAgent mPushAgent, String alias, String type) {
        mPushAgent.addExclusiveAlias(alias, type,
                new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean isSuccess, String message) {

                    }
                });
    }

    public static void removeAlias(PushAgent mPushAgent, String alias, String type) {
        mPushAgent.removeAlias(alias, type, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {

            }
        });
    }

    /**
     * 线上版本请关掉该配置检查，或者去掉这行检查代码（sdk默认是不做该项检查的）
     *
     * @param mPushAgent
     * @param pushCheck
     */
    public static void setPushCheck(PushAgent mPushAgent, boolean pushCheck) {
        mPushAgent.setPushCheck(pushCheck);
    }

    /**
     * 注销回调：IUmengCallback；当关闭【友盟+】推送时，可调用以下代码（请在Activity内调用）
     *
     * @param mPushAgent
     */
    public static void closeUPush(PushAgent mPushAgent, @NonNull Activity activity) {
        mPushAgent.disable(new IUmengCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }

    /**
     * 若调用关闭推送后，想要再次开启推送，则需要调用以下代码（请在Activity内调用）
     *
     * @param mPushAgent
     * @param activity
     */
    public static void openUPush(PushAgent mPushAgent, @NonNull Activity activity) {
        mPushAgent.enable(new IUmengCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }

    /**
     * 若开发者需要实现对消息的完全自定义处理，则可以继承 UmengMessageService, 实现自己的Service来完全控制达到消息的处理。
     * 实现一个类，继承 UmengMessageService， 重写onMessage(Context context, Intent intent) 方法
     *
     * @param mPushAgent
     * @param clazz
     * @param <U>
     */
    public static <U extends UmengMessageService> void setCustomService(PushAgent mPushAgent, Class<U> clazz) {
        mPushAgent.setPushIntentServiceClass(clazz);
    }

    /**
     * 设置实时地理位置推送发送频率，可选范围为2秒/次 ~ 30分钟/次。若不调用该方法，则默认10分钟发送一次地理位置信息
     *
     * @param mPushAgent
     * @param seconds
     */
    public static void setLocationInterval(PushAgent mPushAgent, int seconds) {
        mPushAgent.setLocationInterval(seconds);
    }

}
