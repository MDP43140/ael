/*
 * SPDX-FileCopyrightText: 2024 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
// TODO: check if POST_NOTIFICATION is granted, and if not fallback to ErrorActivity
class ErrorLogger(private val ctx: Context): Thread.UncaughtExceptionHandler {
	private val listeners = mutableListOf<OnErrorListener>()
	// JvmField exposes the variable as public field in the Java bytecode
	// without it, will throw "private access" error
	@JvmField var isLog = true
	@JvmField var isActivity = true
	@JvmField var isNotification = false // TODO: not working (msg not supplied into ErrorActivity) :(
	@JvmField var isToast = true
	init {
		Thread.setDefaultUncaughtExceptionHandler(this)
		instance = this
	}
	fun addListener(l: OnErrorListener){
		listeners.add(l)
	}
	fun removeListener(l: OnErrorListener){
		listeners.remove(l)
	}
	fun handleError(msg: String){
		if (isLog) Log.e("AEL_Error", msg)
		if (!CommonFunctions.canPostNotification(ctx)){
			isNotification = false
			isToast = true
			isLog = true
			isActivity = true
		}
		if (false && isToast){ // TODO: not working :(
			val message = if (isActivity || isNotification)
				"The app is crashed"
			else
				"The app is crashed, error log is written, use logcat to see it\n" + msg
			Toast.makeText(ctx.getApplicationContext(),message,Toast.LENGTH_SHORT).show()
		}
		if (isActivity || isNotification) {
			val intent = Intent(ctx,ErrorActivity::class.java).apply {
				flags = Intent.FLAG_ACTIVITY_NEW_TASK
				putExtra(Constants.EXTRA_MESSAGE,msg)
			}
			if (isNotification){
				try {
					CommonFunctions.makeNotificationChannel(
						ctx,
						ctx.getString(R.string.errLog),
						ctx.getString(R.string.errLog_notification_sum),
						Constants.NOTIFICATION_CHANNEL_ERROR,
						NotificationManager.IMPORTANCE_DEFAULT
					)
					CommonFunctions.sendNotification(
						ctx,
						Constants.NOTIFICATION_ID_ERROR,
						CommonFunctions.getNotificationBuilder(ctx,Constants.NOTIFICATION_CHANNEL_ERROR)
							.setContentTitle(ctx.getString(R.string.errLog_msg))
							.setContentText(msg)
							.setSmallIcon(R.drawable.ic_bug)
							.setContentIntent(PendingIntent.getActivity(ctx,0,intent,PendingIntent.FLAG_MUTABLE))
							.setAutoCancel(true)
							.setPriority(NotificationCompat.PRIORITY_HIGH)
							.build()
					)
				} catch (e: Exception){
					isNotification = false
					isActivity = true
					handleError(msg + "\n\nUnable to send error notification:\n" + e.stackTraceToString())
					return
				}
			} else {
				ctx.startActivity(intent)
			}
		}
	}
	fun handleError(throwable: Throwable){
		handleError(Log.getStackTraceString(throwable))
	}
	fun handleError(exception: Exception){
		handleError(exception.stackTraceToString())
	}
	override fun uncaughtException(t: Thread,e: Throwable) {
		handleError(e)
		listeners.forEach { it.onError(t, e) }
		System.exit(1)
	}
	interface OnErrorListener {
		fun onError(thread: Thread, throwable: Throwable)
	}
	companion object {
		@JvmField var instance: ErrorLogger? = null
		@JvmField var reportUrl: String? = null
	}
}