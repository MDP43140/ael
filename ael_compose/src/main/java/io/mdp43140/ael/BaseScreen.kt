/*
 * SPDX-FileCopyrightText: 2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mdp43140.ael.CommonFunctions
@Composable
fun BaseScreen(
	colorScheme: ColorScheme,
	message: String = "No message?",
	messageMarkdown: String = "No message?",
	reportUrl: String?
){
	val ctx = LocalContext.current
	val scrollHState = rememberScrollState()
	val scrollVState = rememberScrollState()
	Column(
		modifier = Modifier
			.padding(horizontal = 12.dp,vertical = 0.dp)
			.fillMaxSize()
	){
		Text(
			color = colorScheme.onBackground,
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 0.dp,vertical = 8.dp),
			style = MaterialTheme.typography.titleLarge,
			text = stringResource(R.string.errLog_msg),
			textAlign = TextAlign.Center
		)
		Text(color = colorScheme.onBackground,modifier = Modifier.padding(bottom = 8.dp),text = stringResource(R.string.details))
		// message detail
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
				.clip(RoundedCornerShape(8.dp))
				.background(colorScheme.surfaceVariant)
				.horizontalScroll(scrollHState)
				.verticalScroll(scrollVState)
		){
			SelectionContainer {
				Text(
					color = colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium,
					text = message,
					fontFamily = FontFamily.Monospace,
					modifier = Modifier.padding(8.dp),
				)
			}
		}
		// 3 buttons
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 0.dp,vertical = 8.dp)
		){
			Button(modifier = Modifier.fillMaxWidth(),onClick = {
				CommonFunctions.shareFile(ctx,message,"text/plain")
			}){ Text(stringResource(R.string.share)) }
			Spacer(modifier = Modifier.height(8.dp))
			Button(modifier = Modifier.fillMaxWidth(),onClick = {
				CommonFunctions.shareFile(ctx,messageMarkdown,"text/plain")
			}){ Text(stringResource(R.string.errLog_shareLogFormatted)) }
			if (reportUrl != null){
				Spacer(modifier = Modifier.height(8.dp))
				Button(modifier = Modifier.fillMaxWidth(),onClick = {
					val intent = Intent(
						Intent.ACTION_VIEW,
						Uri.parse(reportUrl)
					)
					ctx.startActivity(intent)
				}){ Text(stringResource(R.string.errLog_reportIssue)) }
			}
		}
	}
}
