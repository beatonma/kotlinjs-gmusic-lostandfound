dependencies {
    implementation(Dependency.COROUTINES_CORE_JS)
    implementation(Dependency.KOTLIN_HTML_JS)
    implementation(project(":chrome-platform"))
    implementation(project(":common"))
    implementation(project(":dom"))
}

kotlin.target.browser {

}
