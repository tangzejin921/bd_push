package com.tzj.bd.push;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

public class Util {
    /**
     * 通知是否被关闭
     */
    public static boolean notifiIsOpen(Context ctx) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(ctx);
        return manager.areNotificationsEnabled();
    }

    /**
     * 通知
     */
    public static void openNotifi(final Context ctx, final View.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx)//
                .setCancelable(false)
                .setIcon(ctx.getApplicationInfo().icon)
                .setTitle(ctx.getApplicationInfo().name)
                .setMessage(
                        "接收到了一条通知,\n" +
                                "但由于通知被关闭\n" +
                                "您将无法查看消息内容,\n" +
                                "请允许通知")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                            intent.putExtra(Settings.EXTRA_APP_PACKAGE,ctx.getPackageName());
                            intent.putExtra(Settings.EXTRA_CHANNEL_ID,ctx.getApplicationInfo().uid);
                            intent.putExtra("app_package", ctx.getPackageName());
                            intent.putExtra("app_uid", ctx.getApplicationInfo().uid);
                            ctx.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            openSetting(ctx);
                        }
                        listener.onClick(null);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        listener.onClick(null);
                    }
                })
                .create();
        alertDialog.show();
    }

    /**
     * 打开应用的设置
     */
    public static void openSetting(Context ctx) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", ctx.getPackageName(), null);
        intent.setData(uri);
        ctx.startActivity(intent);
    }

    /**
     * 得到当前进程名
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
}
