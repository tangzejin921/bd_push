package com.tzj.bd.push;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

public class BaiduPushApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        init(this,"LjPgU2A3mBozEFmYaSbabEIo",null);
    }

    public static void init(Application application, String key, IPush push){
        Resources resources = application.getApplicationContext().getResources();
        PushManager.startWork(application.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,key);
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
                resources.getIdentifier("notification_custom_builder","layout",application.getApplicationInfo().packageName),
                resources.getIdentifier("notification_icon","id",application.getApplicationInfo().packageName),
                resources.getIdentifier("notification_title","id",application.getApplicationInfo().packageName),
                resources.getIdentifier("notification_text","id",application.getApplicationInfo().packageName)
        );
        builder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        builder.setNotificationDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
        builder.setStatusbarIcon(application.getApplicationInfo().icon);
        PushManager.setNotificationBuilder(application,1,builder);
        PushManager.setDefaultNotificationBuilder(application,builder);
        if (push!=null){
            PushReceiver.addPushListener(key,push);
        }
    }
    public static void stopPush(Context ctx){
        PushManager.stopWork(ctx);
    }
    /**
     * 最后一次点击通知的那条消息
     * 调用方法后将被清除
     * @param ctx
     */
    public static PushReceiver.PushMsg getLastClickMsg(Context ctx){
        PushReceiver.PushMsg pushMsg = new PushReceiver.PushMsg();
        SharedPreferences sp = ctx.getSharedPreferences(PushReceiver.class.getName(), Context.MODE_PRIVATE);
        pushMsg.title = sp.getString("title", "");
        pushMsg.title = sp.getString("description", "");
        pushMsg.title = sp.getString("customContentString", "");
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("title","");
        edit.putString("description","");
        edit.putString("customContentString","");
        edit.commit();
        return pushMsg;
    }
}
