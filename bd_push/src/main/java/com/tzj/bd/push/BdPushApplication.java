package com.tzj.bd.push;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.baidu.android.pushservice.BasicPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushNotificationBuilder;

public class BdPushApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        init(this,"gR2XeqrxcYuZf3b0RxIZzB4Y",null);
    }

    public static void init(Application application, String key, IPush push){
        //如果不是主进程不让其执行
        if (!Util.getProcessName(application).equals(application.getPackageName())){
            return;
        }
        PushManager.startWork(application.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,key);
        //自定义样式，效果不是很好
//        Resources resources = application.getApplicationContext().getResources();
//        PushNotificationBuilder builder = new CustomPushNotificationBuilder(
//                resources.getIdentifier("notification_custom_builder","layout",application.getApplicationInfo().packageName),
//                resources.getIdentifier("notification_icon","id",application.getApplicationInfo().packageName),
//                resources.getIdentifier("notification_title","id",application.getApplicationInfo().packageName),
//                resources.getIdentifier("notification_text","id",application.getApplicationInfo().packageName)
//        );
//
//        builder.setChannelId(application.getPackageName());
//        builder.setChannelName(getAppName(application));
//        builder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
//        builder.setNotificationDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
//        builder.setStatusbarIcon(application.getApplicationInfo().icon);
//        PushManager.setNotificationBuilder(application,1,builder);
        //默认通知样式
        PushNotificationBuilder builder = new BasicPushNotificationBuilder();
        builder.setChannelId(application.getPackageName());
        builder.setChannelName(getAppName(application));
        PushManager.setDefaultNotificationBuilder(application,builder);
        if (push!=null){
            PushReceiver.addPushListener(key,push);
        }
    }
    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int identifier = context.getResources().getIdentifier("app_name", "string", context.getPackageName());
        return context.getString(identifier);
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
        pushMsg.description = sp.getString("description", "");
        pushMsg.customContentString = sp.getString("customContentString", "");
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("title","");
        edit.putString("description","");
        edit.putString("customContentString","");
        edit.commit();
        return pushMsg;
    }

    /**
     * 百度的推送 账号
     */
    public static PushReceiver.PushInfo getPushInfo(Context ctx){
        PushReceiver.PushInfo pushInfo = new PushReceiver.PushInfo();
        SharedPreferences sp = ctx.getSharedPreferences(PushReceiver.PushInfo.class.getName(), Context.MODE_PRIVATE);
        pushInfo.appId = sp.getString("appId", "");
        pushInfo.userId = sp.getString("userId", "");
        pushInfo.channelId = sp.getString("channelId", "");
        pushInfo.requestId = sp.getString("requestId", "");
        return pushInfo;
    }
}
