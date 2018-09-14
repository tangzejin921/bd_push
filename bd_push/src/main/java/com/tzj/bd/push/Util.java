package com.tzj.bd.push;

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
        AlertDialog alertDialog = new AlertDialog.Builder(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog)//
                .setCancelable(false)
                .setMessage(
                        "接收到了一条通知,\n" +
                                "但由于通知被关闭\n" +
                                "您将无法查看消息内容,请允许通知")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
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
//        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
}
