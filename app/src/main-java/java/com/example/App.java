package com.example;
import android.app.Application;
import android.content.Context;

import io.mdp43140.ael.ErrorLogger;
public class App extends Application {
	@Override protected void attachBaseContext(Context base){
		super.attachBaseContext(base);
		// should only be initialized once
		if (ErrorLogger.instance == null){
			new ErrorLogger(base);

			// Set your URL where to report errors
			//ErrorLogger.instance.reportUrl = "https://github.com/user/project/issues/new"

			// Customize what to do with the error message
			//ErrorLogger.instance.isLog = true;          // writes error to logcat
			//ErrorLogger.instance.isActivity = true;     // opens an activity to display the stacktrace
			//ErrorLogger.instance.isNotification = true; // sends a notification that you can click to open the error activity
			//ErrorLogger.instance.isToast = true;        // shows a toast
		}
	}
	@Override public void onCreate(){
		super.onCreate();
	}
}
