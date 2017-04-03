package com.optimalbd.dibosh;

import android.app.Application;

import com.optimalbd.dibosh.Ulility.LocaleHelper;


/**
 * Created by gunhan.sancar on 20/12/2016.
 */

public class MainApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		LocaleHelper.onCreate(this, "bn");
	}
}
