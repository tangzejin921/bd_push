package com.tzj.bd.push;


import android.content.Context;

import com.baidu.android.pushservice.PushManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;

/**
 *
 */
public class BdPushPlugin implements EventChannel.StreamHandler,IPush{
    public static EventChannel.EventSink eventSink;
    private Context mContext;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        BdPushPlugin bdPushPlugin = new BdPushPlugin(registrar.activity().getApplicationContext());
        if (registrar.activity() instanceof FlutterView.Provider){
            FlutterView flutterView = ((FlutterView.Provider) registrar.activity()).getFlutterView();
            FlutterNativeView flutterNativeView = flutterView.getFlutterNativeView();
            new EventChannel(flutterNativeView,BdPushPlugin.class.getSimpleName())
                    .setStreamHandler(bdPushPlugin);
            PushReceiver.addPushListener(BdPushPlugin.class.getSimpleName(),bdPushPlugin);
            registrar.addViewDestroyListener(new PluginRegistry.ViewDestroyListener() {
                @Override
                public boolean onViewDestroy(FlutterNativeView flutterNativeView) {
                    PushReceiver.removeListener(flutterNativeView.toString());
                    eventSink = null;
                    return false;
                }
            });
        }else{
            throw new RuntimeException("需要 FlutterView.Provider");
        }
    }

    private BdPushPlugin(Context context) {
        mContext = context;
    }

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        BdPushPlugin.eventSink = eventSink;
        PushReceiver.PushInfo pushInfo = BdPushApplication.getPushInfo(mContext);
        if (!pushInfo.isEmpty()
                && PushManager.isPushEnabled(mContext)){
            onBind(mContext,0,pushInfo.appId,pushInfo.userId,pushInfo.channelId,pushInfo.requestId);
        }
    }

    @Override
    public void onCancel(Object o) {
        BdPushPlugin.eventSink = null;
    }

    //===============================
    @Override
    public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {
        Map<String,String> map = new HashMap<>();
        map.put("key","onBind");
        map.put("appId",appid);
        map.put("userId",userId);
        map.put("channelId",channelId);
        map.put("requestId",requestId);
        if (eventSink!=null){
            eventSink.success(map);
        }
    }

    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        Map<String,String> map = new HashMap<>();
        map.put("key","onUnbind");
        map.put("requestId",requestId);
        if (eventSink!=null){
            eventSink.success(map);
        }
    }

    @Override
    public void onSetTags(Context context, int errorCode, List<String> successTags, List<String> failTags, String requestId) {

    }

    @Override
    public void onDelTags(Context context, int errorCode, List<String> successTags, List<String> failTags, String requestId) {

    }

    @Override
    public void onListTags(Context context, int errorCode, List<String> tags, String requestId) {

    }

    @Override
    public void onMessage(Context context, String message, String customContentString) {
        Map<String,String> map = new HashMap<>();
        map.put("key","onMessage");
        map.put("message",message);
        map.put("customContentString",customContentString);
        if (eventSink!=null){
            eventSink.success(map);
        }
    }

    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {
        Map<String,String> map = new HashMap<>();
        map.put("key","onNotificationArrived");
        map.put("title",title);
        map.put("description",description);
        map.put("customContentString",customContentString);
        if (eventSink!=null){
            eventSink.success(map);
        }
    }

    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        Map<String,String> map = new HashMap<>();
        map.put("key","onNotificationClicked");
        map.put("title",title);
        map.put("description",description);
        map.put("customContentString",customContentString);
        if (eventSink!=null){
            eventSink.success(map);
        }
    }
}
