/*
 * SPDX-FileCopyrightText: 2024-2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
import java.util.Properties // used by signingConfigs.release (ksProps variable)
import com.android.build.gradle.tasks.PackageAndroidArtifact // used by empty app-metadata.properties

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
}
android {
	compileSdk = 35
	buildToolsVersion = "35.0.0"
	namespace = "com.example"
	defaultConfig {
		applicationId = "io.mdp43140.ael.test"
		minSdk = 21
		targetSdk = compileSdk
		versionCode = 1
		versionName = "1.0.0"
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		testInstrumentationRunnerArguments["disableAnalytics"] = "true"
		vectorDrawables {
			useSupportLibrary = false
		}
	}
	signingConfigs {
		create("main"){
			val ksPropsFile = rootProject.file(".signing/keystore.properties")
			if (ksPropsFile.exists()){
				val ksProps = Properties().apply {
					load(ksPropsFile.inputStream())
				}
				keyAlias = ksProps["keyAlias"] as String
				keyPassword = ksProps["keyPassword"] as String
				storeFile = file(ksProps["storeFile"] as String)
				storePassword = ksProps["storePassword"] as String
			}
		}
	}
	lint {
		abortOnError = false
		checkReleaseBuilds = false // we did thousands of these on debug builds already...
		lintConfig = file("lint.xml")
	}
	buildTypes {
		debug {
			applicationIdSuffix = ".debug"
			isDebuggable = true
		}
		release {
		//isMinifyEnabled = true
		//isShrinkResources = true
		//crunchPngs = true // no longer needed, since the PNGs are optimized in the first place before compiling
			isDebuggable = false
			isProfileable = false
			isJniDebuggable = false
			isPseudoLocalesEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
			signingConfig = signingConfigs.getByName("main")
			vcsInfo.include = false
		//postprocessing {
		//	isRemoveUnusedCode = true
		//	isRemoveUnusedResources = true
		//	isObfuscate = false
		//	isOptimizeCode = true
		//}
		}
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
	// makes the app 1MB bigger :(
		dex {
			useLegacyPackaging = false
		}
		jniLibs {
			useLegacyPackaging = false
		}
		resources {
			excludes += listOf(
				"assets/dexopt/baseline.prof",
				"assets/dexopt/baseline.profm",
				"kotlin/**.kotlin_builtins",
				"META-INF/**",
				"DebugProbesKt.bin",
				"kotlin-tooling-metadata.json"
			)
		}
	}
	dependenciesInfo {
		includeInApk = false
		includeInBundle = false
	}
	tasks.withType<PackageAndroidArtifact> {
		doFirst { appMetadata.asFile.orNull?.writeText("") }
	}
}
dependencies {
	// Error logger
	implementation(project(":ael_kt"))
}
