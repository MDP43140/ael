/*
 * SPDX-FileCopyrightText: 2024-2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.mdp43140.ael.databinding.ActivityErrorBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class ErrorActivity: AppCompatActivity(){
	private lateinit var binding: ActivityErrorBinding
	override fun onCreate(savedInstanceState: Bundle?){
		super.onCreate(savedInstanceState)
		binding = ActivityErrorBinding.inflate(layoutInflater)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
			// Handle Edge-to-edge
			binding.root.setOnApplyWindowInsetsListener { v, windowInsets ->
				val insets = windowInsets.getInsets(
					WindowInsets.Type.systemBars() or
					WindowInsets.Type.displayCutout()
				)
				v.setPadding(
					insets.left,
					insets.top,
					insets.right,
					insets.bottom
				)
				WindowInsets.CONSUMED
			}
		}
		val self: ErrorActivity = this
		val appVersion = packageManager.getPackageInfo(packageName,0).versionName
		val appLang = Locale.getDefault().language
		val osVersion = (System.getProperty("os.name") ?: "Android") +
			" ${Build.VERSION.BASE_OS} ${Build.VERSION.RELEASE} - ${Build.VERSION.SDK_INT}"
		val msg = intent.getStringExtra(Constants.EXTRA_MESSAGE) ?: "No message?"
		// why not DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(LocalDateTime.now())?
		// cuz on A7-, it throws ClassDefNotFound error :(
		val time = SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date())
		val text = StringBuilder()
			.append("Package name: ${packageName}\n")
			.append("App language: ${appLang}\n")
			.append("App version : ${appVersion}\n")
			.append("Device      : ${Build.BRAND} ${Build.MODEL}\n")
			.append("OS Version  : ${osVersion}\n")
			.append("GMT Time    : ${time}\n\n")
			.append(msg)
			.toString()
		val textMarkdown = StringBuilder()
			.append("## Exception\n")
			.append("* __Package name:__ ${packageName}\n")
			.append("* __App language:__ ${appLang}\n")
			.append("* __App version:__ ${appVersion}\n")
			.append("* __Device:__ ${Build.BRAND} ${Build.MODEL}\n")
			.append("* __OS Version:__ ${osVersion}\n")
			.append("* __GMT Time:__ ${time}\n")
			.append("<details><summary><b>Crash log</b></summary><p>\n\n")
			.append("```\n${msg}\n```\n")
			.append("</details><hr>")
			.toString()
//	val textHTML: String = StringBuilder()
//		.append("<h2>Exception</h2><ul>")
//		.append("<li><b>Package name</b>: ${packageName}</li>")
//		.append("<li><b>App language</b>: ${appLang}</li>")
//		.append("<li><b>App version</b>: ${appVersion}</li>")
//		.append("<li><b>Device</b>: ${Build.BRAND} ${Build.MODEL}</li>")
//		.append("<li><b>OS Version</b>: ${osVersion}</li>")
//		.append("<li><b>GMT Time</b>: ${time}</li></ul>")
//		.append("<details><summary><b>Crash log</b></summary><p></li></li>")
//		.append("<code>\n${msg}\n</code>")
//		.append("</details><hr>")
//		.toString()
		binding.apply {
			setContentView(root)
			error.typeface = Typeface.MONOSPACE
			error.text = text
			shareLogBtn.setOnClickListener {
				CommonFunctions.shareFile(self,text,"text/plain")
			}
			shareFormattedLogBtn.setOnClickListener {
				CommonFunctions.shareFile(self,textMarkdown,"text/plain")
			}
			if (ErrorLogger.reportUrl != null){
				reportIssueBtn.setVisibility(0)
				reportIssueBtn.setOnClickListener {
					startActivity(Intent(
						Intent.ACTION_VIEW,
						Uri.parse(ErrorLogger.reportUrl)
					))
				}
			}
		}
	}
	@Deprecated("is there a way to intercept back pressed event without using deprecated method and without bloat as well?")
	override fun onBackPressed(){
		finishAndRemoveTask()
	}
}
