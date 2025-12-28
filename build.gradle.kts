allprojects {
	version = "1.1.2"
	group = "com.github.mdp43140.ael"
}
plugins {
	alias(libs.plugins.android.application) apply false
	alias(libs.plugins.android.library) apply false
	alias(libs.plugins.kotlin.android) apply false
	alias(libs.plugins.kotlin.compose) apply false
	id("maven-publish")
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
						version = "1.1.2"
					}
				}
			}
		}
	}
}
