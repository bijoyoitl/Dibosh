package com.optimalbd.dibosh.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import com.optimalbd.dibosh.BirthDayActivity;

/**
 * Created by Gafur on 21-11-15.
 */
public class BirthMarriageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int age = intent.getIntExtra("age", 0);
        String type = intent.getStringExtra("type");
        int mYear = intent.getIntExtra("mYear", 0);

        Intent intent1 = new Intent(context, BirthDayActivity.class);
        intent1.putExtra("age", age);
        intent1.putExtra("type", type);

        if (type.equals("2")) {
            intent1.putExtra("mYear", mYear);
        }

        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
