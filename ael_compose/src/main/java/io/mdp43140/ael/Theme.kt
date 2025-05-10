/*
 * SPDX-FileCopyrightText: 2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
@Composable
fun getColorScheme(
	blackTheme: Boolean = false,
	darkTheme: Boolean = isSystemInDarkTheme(),
	dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
): ColorScheme {
	return if (darkTheme){
		val colorScheme = if (dynamicColor)
				dynamicDarkColorScheme(LocalContext.current)
			else
				darkColorScheme()
		if (blackTheme)
			colorScheme.copy(background = Color.Black)
		else
			colorScheme
	} else {
		if (dynamicColor)
			dynamicLightColorScheme(LocalContext.current)
		else
			lightColorScheme()
	}
}
