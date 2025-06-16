/*
 * SPDX-FileCopyrightText: 2024-2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.SharedPreferences
import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.DecimalFormat
//import io.mdp43140.ael.Constants
//import io.mdp43140.ael.ErrorActivity
import kotlin.system.exitProcess
object CommonFunctions {
	fun makeNotificationChannel(ctx: Context, name: String, description: String?, channelName: String, importance: Int){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
			val channel = NotificationChannel(channelName, name, importance)
			channel.description = description
			(ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
		}
	}
	fun getNotificationBuilder(ctx: Context, channelName: String): NotificationCompat.Builder{
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
			NotificationCompat.Builder(ctx,channelName)
		} else {
			NotificationCompat.Builder(ctx)
		}
	}
	fun sendNotification(mgr: NotificationManager, id: Int, notification: Notification){
		mgr.notify(id, notification)
	}
	fun sendNotification(ctx: Context, id: Int, notification: Notification){
		sendNotification(ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager, id, notification)
	}
	fun sendNotification(mgr: NotificationManager, id: Int, notification: NotificationCompat.Builder){
		sendNotification(mgr, id, notification.build())
	}
	fun sendNotification(ctx: Context, id: Int, notification: NotificationCompat.Builder){
		sendNotification(ctx, id, notification.build())
	}
	fun canPostNotification(ctx: Context): Boolean {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
			ctx.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
	}
	fun shareFile(ctx: Context, text: String, mimeType: String){
		ctx.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
			type = mimeType
			putExtra(Intent.EXTRA_TEXT, text)
		},null))
	}
	fun shareFile(ctx: Context, uri: Uri, mimeType: String){
		ctx.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
			type = mimeType
			putExtra(Intent.EXTRA_STREAM, uri)
		},null))
	}
}
