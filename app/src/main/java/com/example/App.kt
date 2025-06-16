package com.example
import android.app.Application
import android.content.Context

import io.mdp43140.ael.ErrorLogger
class App: Application(){
	override fun attachBaseContext(base: Context){
		super.attachBaseContext(base)
		// should only be initialized once
		if (ErrorLogger.instance == null){
			ErrorLogger(base)
			ErrorLogger.instance?.apply {
				// Set your URL where to report errors
				//reportUrl = "https://github.com/user/project/issues/new"

				// Customize what to do with the error message
				isLog = true          // writes error to logcat
				isActivity = true     // opens an activity to display the stacktrace
				isNotification = true // sends a notification that you can click to open the error activity
				isToast = true        // shows a toast
			}
		}
	}
	override fun onCreate(){
		super.onCreate()
	}
}
