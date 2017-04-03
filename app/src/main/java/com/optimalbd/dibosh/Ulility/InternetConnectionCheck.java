package com.optimalbd.dibosh.Ulility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by engrb on 03-Feb-17.
 */

public class InternetConnectionCheck {

    public static boolean isConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;

    }

}