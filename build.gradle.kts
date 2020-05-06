fun <T> T.dump(message: String = "[dump]"): T {
    return this.also { println("$message: $this") }
}

plugins {
    id("org.jetbrains.kotlin.js") version Version.KOTLIN
    distribution
}

group = App.PACKAGE_NAME
version = App.VERSION

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven ("https://kotlin.bintray.com/kotlinx")
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.js")
    apply(plugin = "kotlin-dce-js")

    repositories {
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven ("https://kotlin.bintray.com/kotlinx")
        jcenter()
    }

    dependencies {
        implementation(kotlin("stdlib-js"))
    }

    kotlin {
        target {
            browser {}
            useCommonJs()
            produceExecutable()
        }
    }

    copy {
        from("$buildDir/distributions")
        include(*App.Build.allProjectsResourceFilePatterns)
        into("${rootProject.buildDir}/flatres/")
    }
}

distributions {
    create("debug") {
        distributionBaseName.set("${App.NAME}-debug")
        contents {
            from("manifest.json") {
                expand(
                    "name" to "${App.NAME}-debug",
                    "version" to App.VERSION,
                    "content_urls" to App.Manifest.CONTENT_SCRIPT_URL_PATTERNS.wrapEach(),
                    "permissions" to App.Manifest.PERMISSIONS.wrapEach()
                )
            }
            from("**/*.html")
            from("$buildDir/flatres/")

            into("/")
        }
    }
}
