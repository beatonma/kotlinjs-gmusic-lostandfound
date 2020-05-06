pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven ("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

rootProject.name = "gmusic2"
include(":content")
include(":background")
include(":popup")
include(":options")
include(":dom")
include(":common")
include(":chrome-platform")
