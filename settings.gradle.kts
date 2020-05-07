pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "gmusic2"

// App
include(":background")
include(":common")
include(":content")
include(":dom")
include(":popup")
include(":options")

// Browser interface
include(":browser")
include(":chrome-platform")
