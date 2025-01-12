/*
 * SPDX-FileCopyrightText: 2024-2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.app.Notification;
import java.util.ArrayList;
import java.util.List;
public class ErrorLogger implements Thread.UncaughtExceptionHandler {
	public static ErrorLogger instance;
	public static String reportUrl;
	private Context ctx;
	private List<OnErrorListener> listeners = new ArrayList<>();
	public boolean isLog = true;
	public boolean isActivity = true;
	public boolean isNotification = true;
	public boolean isToast = true;
	public ErrorLogger(Context ctx) {
		this.ctx = ctx;
		Thread.setDefaultUncaughtExceptionHandler(this);
		instance = this;
	}
	public void addListener(OnErrorListener l) {
		listeners.add(l);
	}
	public void removeListener(OnErrorListener l) {
		listeners.remove(l);
	}
	public void handleError(String msg) {
		if (isLog) Log.e("AEL_Error", msg);
		if (!CommonFunctions.canPostNotification(ctx)){
			isNotification = false;
			isToast = true;
			isLog = true;
			isActivity = true;
		}
		if (false && isToast) { // TODO: not working :(
			String message = isActivity || isNotification ?
					"The app is crashed" :
					"The app is crashed, error log is written, use logcat to see it\n" + msg;
			Toast.makeText(ctx.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
		}
		if (isActivity || isNotification) {
			Intent intent = new Intent(ctx, ErrorActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(Constants.EXTRA_MESSAGE, msg);
			if (isNotification) {
				try {
					CommonFunctions.makeNotificationChannel(
							ctx,
							ctx.getString(R.string.errLog),
							ctx.getString(R.string.errLog_notification_sum),
							Constants.NOTIFICATION_CHANNEL_ERROR,
							NotificationManager.IMPORTANCE_DEFAULT
					);
					CommonFunctions.sendNotification(
							ctx,
							Constants.NOTIFICATION_ID_ERROR,
							CommonFunctions.getNotificationBuilder(ctx, Constants.NOTIFICATION_CHANNEL_ERROR)
									.setContentTitle(ctx.getString(R.string.errLog_msg))
									.setContentText(msg)
									.setSmallIcon(R.drawable.ic_bug)
									.setContentIntent(PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_MUTABLE))
									.setAutoCancel(true)
									.setPriority(Notification.PRIORITY_HIGH)
									.build()
					);
				} catch (Exception e) {
					isNotification = false;
					isActivity = true;
					handleError(msg + "\n\nUnable to send error notification:\n" + CommonFunctions.stackTraceToString(e));
					return;
				}
			} else {
				ctx.startActivity(intent);
			}
		}
	}
	public void handleError(Throwable t) {
		handleError(Log.getStackTraceString(t));
	}
	public void handleError(Exception e) {
		handleError(CommonFunctions.stackTraceToString(e));
	}
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		handleError(e);
		for (OnErrorListener listener : listeners) {
			listener.onError(t, e);
		}
		System.exit(1);
	}
	public interface OnErrorListener {
		void onError(Thread thread, Throwable throwable);
	}
}