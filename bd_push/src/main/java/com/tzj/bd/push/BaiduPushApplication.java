package com.tzj.bd.push;

import android.app.Application;
import android.app.Notification;

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
        PushManager.startWork(application.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,key);
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
                R.layout.notification_custom_builder,
                R.id.notification_icon,
                R.id.notification_title,
                R.id.notification_text
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
}
