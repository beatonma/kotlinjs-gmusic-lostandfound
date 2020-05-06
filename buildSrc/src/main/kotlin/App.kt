object App {
    const val NAME = "gmusic2"
    const val PACKAGE_NAME = "org.beatonma.gmusic2"
    const val VERSION = "0.1"

    object Build {
        const val DEBUG = true
        val allProjectsResourceFilePatterns = if (DEBUG) {
            arrayOf("**/*.js", "**/*.html", "**/*.css", "**/*.js.map")
        }
        else {
            arrayOf("**/*.js", "**/*.html", "**/*.css")
        }
    }

    object Manifest {
        val PERMISSIONS = arrayOf(
            "tabs"
        )

        val CONTENT_SCRIPT_URL_PATTERNS = arrayOf(
//            "https://beatonma.org/*"
            "https://play.google.com/music/listen*"
        )
    }
}

fun <T> Array<T>.wrapEach(char: Char = '"') = map { "$char$it$char" }