package com.tzj.bd.push;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.LruCache;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Push消息处理receiver。请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值；
 * onMessage用来接收透传消息； onSetTags、onDelTags、onListTags是tag相关操作的回调；
 * onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调
 * 返回值中的errorCode，解释如下：
 * 0 - Success
 * 10001 - Network Problem
 * 10101 - Integrate Check Error
 * 30600 - Internal Server Error
 * 30601 - Method Not Allowed
 * 30602 - Request Params Not Valid
 * 30603 - Authentication Failed
 * 30604 - Quota Use Up Payment Required
 * 30605 - Data Required Not Found
 * 30606 - Request Time Expires Timeout
 * 30607 - Channel Token Timeout
 * 30608 - Bind Relation Not Found
 * 30609 - Bind Number Too Many
 * 当您遇到以上返回错误时，如果解释不了您的问题，请用同一请求的返回值requestId和errorCode联系我们追查问题。
 *
 */
public class PushReceiver extends PushMessageReceiver {
    private static LruCache<String,IPush> weakReference = new LruCache<>(10);//注意：只里给了10

    public static void addPushListener(String tag,IPush push){
        if (tag!=null){
            weakReference.put(tag,push);
        }
    }
    public static void removeListener(String str){
        if (str!=null){
            weakReference.remove(str);
        }
    }
    public static void clear(){
        weakReference.evictAll();
    }
    @Override
    public void onBind(Context context, int errCode, String appid, String userId, String channelId, String secretKey) {
        Set<Map.Entry<String, IPush>> entries = weakReference.snapshot().entrySet();
        for (Map.Entry<String, IPush> entry:entries) {
            entry.getValue().onBind(context,errCode,appid,userId,channelId,secretKey);
        }
    }

    @Override
    public void onUnbind(Context context, int i, String s) {
        Set<Map.Entry<String, IPush>> entries = weakReference.snapshot().entrySet();
        for (Map.Entry<String, IPush> entry:entries) {
            entry.getValue().onUnbind(context,i,s);
        }
    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Set<Map.Entry<String, IPush>> entries = weakReference.snapshot().entrySet();
        for (Map.Entry<String, IPush> entry:entries) {
            entry.getValue().onSetTags(context,i,list,list1,s);
        }
    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Set<Map.Entry<String, IPush>> entries = weakReference.snapshot().entrySet();
        for (Map.Entry<String, IPush> entry:entries) {
            entry.getValue().onDelTags(context,i,list,list1,s);
        }
    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {
        Set<Map.Entry<String, IPush>> entries = weakReference.snapshot().entrySet();
        for (Map.Entry<String, IPush> entry:entries) {
            entry.getValue().onListTags(context,i,list,s);
        }
    }

    @Override
    public void onMessage(Context context, String s, String s1) {
        Set<Map.Entry<String, IPush>> entries = weakReference.snapshot().entrySet();
        for (Map.Entry<String, IPush> entry:entries) {
            entry.getValue().onMessage(context,s,s1);
        }
    }

    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {
        OpenNotifiActivity.start(context);
        Set<Map.Entry<String, IPush>> entries = weakReference.snapshot().entrySet();
        for (Map.Entry<String, IPush> entry:entries) {
            entry.getValue().onNotificationArrived(context,title,description,customContentString);
        }
    }

    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        try {
            SharedPreferences sp = context.getSharedPreferences(PushReceiver.class.getName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("title",title);
            edit.putString("description",description);
            edit.putString("customContentString",customContentString);
            edit.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        Set<Map.Entry<String, IPush>> entries = weakReference.snapshot().entrySet();
        for (Map.Entry<String, IPush> entry:entries) {
            entry.getValue().onNotificationClicked(context,title,description,customContentString);
        }
    }

    public static class PushMsg{
        public String title;
        public String description;
        public String customContentString;
        public boolean isEmpty(){
            return TextUtils.isEmpty(title)&&TextUtils.isEmpty(description)&&TextUtils.isEmpty(customContentString);
        }
    }

}
