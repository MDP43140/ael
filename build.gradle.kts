allprojects {
	version = "1.0.0"
	group = "com.github.mdp43140.ael"
}
plugins {
//auto suffixed with .gradle.plugin
	id("com.android.application") version "8.7.3" apply false
	id("com.android.library") version "8.7.3" apply false
	id("maven-publish")
	kotlin("android") version "2.1.0" apply false // kotlin("android") == "org.jetbrains.kotlin.android"
}
tasks.withType(JavaCompile::class.java){
	options.compilerArgs.add("-Xlint:all")
}
tasks.register("clean",Delete::class){
	delete(rootProject.layout.buildDirectory)
}
subprojects {
	afterEvaluate {
		if (plugins.hasPlugin("com.android.library")){
			apply(plugin = "maven-publish")
			publishing {
				publications {
					register<MavenPublication>("ael") {
						afterEvaluate {
							from(components["release"])
						}
						groupId = "com.github.mdp43140"
						artifactId = project.name
						version = "1.0.0"
					}
				}
			}
		}
	}
}