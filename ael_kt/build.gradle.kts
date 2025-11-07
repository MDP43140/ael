/*
 * SPDX-FileCopyrightText: 2024-2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
}
kotlin {
	jvmToolchain(21)
}
android {
	compileSdk = 35
	buildToolsVersion = "35.0.0"
	namespace = "io.mdp43140.ael"
	defaultConfig {
		minSdk = 21
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = false
		}
	}
	lint {
		checkReleaseBuilds = false
		abortOnError = false
	}
	buildTypes {
		release {
		//isMinifyEnabled = true
		//isShrinkResources = true
		//crunchPngs = true // no longer needed, since the PNGs are optimized in the first place before compiling
			isJniDebuggable = false
			isPseudoLocalesEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_21.toString()
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
	buildFeatures {
		buildConfig = false
		compose = false
		viewBinding = false
	}
	packaging {
		//dex {
		//	useLegacyPackaging = false
		//}
		jniLibs {
			useLegacyPackaging = false
		}
	}
	publishing {
		singleVariant("release") {
			withSourcesJar()
		}
	}
}
dependencies {
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.kt)
	implementation(libs.material)
}
