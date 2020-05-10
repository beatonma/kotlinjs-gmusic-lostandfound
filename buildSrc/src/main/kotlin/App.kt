object App {
    const val NAME = "gmusic2"
    const val PACKAGE_NAME = "org.beatonma.gmusic2"
    const val VERSION = "0.1"

    object Build {
        const val DEBUG = true
        val allProjectsResourceFilePatterns = if (DEBUG) {
            arrayOf("**/*.js", "**/*.html", "**/*.css", "**/*.svg", "**/*.js.map")
        }
        else {
            arrayOf("**/*.js", "**/*.html", "**/*.css", "**/*.svg")
        }
    }

    object Manifest {
        val PERMISSIONS = arrayOf(
            "storage",
            "tabs",
            "unlimitedStorage"
        )

        val CONTENT_SCRIPT_URL_PATTERNS = arrayOf(
            "https://play.google.com/music/listen*"
        )

        val RESOURCES = arrayOf(
            "ic_close.svg"
        )
    }
}

fun <T> Array<T>.wrapEach(char: Char = '"') = map { "$char$it$char" }