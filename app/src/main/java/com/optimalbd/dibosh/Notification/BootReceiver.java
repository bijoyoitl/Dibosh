package com.optimalbd.dibosh.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ripon on 1/26/2017.
 */

public class BootReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent intent1 = new Intent(context, NotificationSetService.class);
            context.startService(intent1);
        }

    }

}
