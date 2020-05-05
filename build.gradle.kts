plugins {
    id("org.jetbrains.kotlin.js") version Version.KOTLIN
}

group = App.PACKAGE_NAME
version = App.VERSION

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
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
}

kotlin.target.browser { }