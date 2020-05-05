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
}

dependencies {
    implementation(kotlin("stdlib-js"))
    project(path = ":background")
    project(path = ":content")
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.js")
    apply(plugin = "kotlin-dce-js")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib-js"))
    }

    kotlin.target.browser { }

    copy {
        from("$buildDir/distributions")
        include("**/*.js", "**/*.html", "**/*.css")
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
                    "version" to App.VERSION
                )
            }
            from("**/*.html")
            from("$buildDir/flatres/")

            into("/")
        }
    }
}
