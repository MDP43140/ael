/*
 * SPDX-FileCopyrightText: 2024-2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import io.mdp43140.ael.databinding.ActivityErrorBinding;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class ErrorActivity extends Activity {
	private ActivityErrorBinding binding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityErrorBinding.inflate(getLayoutInflater());
		ErrorActivity self = this;
		String appVersion = "[unable to get app version]";
		try {
			appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (Exception e){}
		String appLang = Locale.getDefault().getLanguage();
		String osVersion = (System.getProperty("os.name") != null ? System.getProperty("os.name") : "Android") +
				(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? " " + Build.VERSION.BASE_OS + " " : " ") +
				Build.VERSION.RELEASE + " - " + Build.VERSION.SDK_INT;
		String msg = getIntent().getStringExtra(Constants.EXTRA_MESSAGE);
		if (msg == null) {
			msg = "No message?";
		}
		String time = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
		String text = new StringBuilder()
			.append("Package name: ").append(getPackageName()).append("\n")
			.append("App language: ").append(appLang).append("\n")
			.append("App version : ").append(appVersion).append("\n")
			.append("Device      : ").append(Build.BRAND).append(" ").append(Build.MODEL).append("\n")
			.append("OS Version  : ").append(osVersion).append("\n")
			.append("GMT Time    : ").append(time).append("\n\n")
			.append(msg)
			.toString();
		String textMarkdown = new StringBuilder()
			.append("## Exception\n")
			.append("* __Package name:__ ").append(getPackageName()).append("\n")
			.append("* __App language:__ ").append(appLang).append("\n")
			.append("* __App version:__ ").append(appVersion).append("\n")
			.append("* __Device:__ ").append(Build.BRAND).append(" ").append(Build.MODEL).append("\n")
			.append("* __OS Version:__ ").append(osVersion).append("\n")
			.append("* __GMT Time:__ ").append(time).append("\n")
			.append("<details><summary><b>Crash log</b></summary><p>\n\n")
			.append("```\n").append(msg).append("\n```\n")
			.append("</details><hr>")
			.toString();
		setContentView(binding.getRoot());
		binding.error.setTypeface(Typeface.MONOSPACE);
		binding.error.setText(text);
		binding.shareLogBtn.setOnClickListener(v -> CommonFunctions.shareFile(self, text, "text/plain"));
		binding.shareFormattedLogBtn.setOnClickListener(v -> CommonFunctions.shareFile(self, textMarkdown, "text/plain"));
		if (ErrorLogger.reportUrl != null) {
			binding.reportIssueBtn.setVisibility(View.VISIBLE);
			binding.reportIssueBtn.setOnClickListener(v -> {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ErrorLogger.reportUrl));
				startActivity(intent);
			});
		}
	}
	@Override
	public void onBackPressed() {
		finishAndRemoveTask();
	}
}