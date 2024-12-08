/*
 * SPDX-FileCopyrightText: 2024 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.io.StringWriter;
import java.io.PrintWriter;
public class CommonFunctions {
	public static final void makeNotificationChannel(Context ctx, String name, String description, String channelName, int importance) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(channelName, name, importance);
			channel.setDescription(description);
			((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
		}
	}
	public static final Notification.Builder getNotificationBuilder(Context ctx, String channelName) {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
				new Notification.Builder(ctx, channelName) :
				new Notification.Builder(ctx);
	}
	public static final void sendNotification(NotificationManager mgr, int id, Notification notification) {
		mgr.notify(id, notification);
	}
	public static final void sendNotification(Context ctx, int id, Notification notification) {
		sendNotification((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE), id, notification);
	}
	public static final void sendNotification(NotificationManager mgr, int id, Notification.Builder notification) {
		sendNotification(mgr, id, notification.build());
	}
	public static final void sendNotification(Context ctx, int id, Notification.Builder notification) {
		sendNotification(ctx, id, notification.build());
	}
	public static final boolean canPostNotification(Context ctx){
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
			ctx.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
	}
	public static final void shareFile(Context ctx, String text, String mimeType) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(mimeType);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		ctx.startActivity(Intent.createChooser(intent, null));
	}
	public static final void shareFile(Context ctx, Uri uri, String mimeType) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(mimeType);
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		ctx.startActivity(Intent.createChooser(intent, null));
	}
	public static final String stackTraceToString(Throwable th) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		th.printStackTrace(printWriter);
		printWriter.flush();
		return stringWriter.toString();
	}
}