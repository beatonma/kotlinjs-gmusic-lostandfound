private fun dependency(group: String, artifact: String, version: String): String {
    return "$group:$artifact:$version"
}

private fun kotlinx(artifact: String, version: String = Version.KOTLIN) =
    dependency(group = "org.jetbrains.kotlinx", artifact = artifact, version = version)


object Version {
    const val KOTLIN = "1.4-M1"
}

object Dependency {
    val COROUTINES_CORE_JS = kotlinx("kotlinx-coroutines-core-js", version = "1.3.5-native-mt-1.4-M1")
    val KOTLIN_HTML_JS = kotlinx("kotlinx-html-js", version = "0.6.12")
}