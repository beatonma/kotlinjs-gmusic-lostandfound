package org.beatonma.browser.gmusicmon.content

import kotlinx.coroutines.*
import org.beatonma.browser.gmusicmon.common.*
import org.beatonma.browser.gmusicmon.content.artists.ArtistDiff
import kotlin.browser.document
import kotlin.browser.window
import kotlin.coroutines.CoroutineContext

private val application = Application()

fun main() {
    browser.onCommand(Messages.UPDATE_ARTISTS) {
        application.update()
    }
}

class Application : CoroutineScope {
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    fun update() {
        val url = document.location?.hash
        url ?: throw Exception("Unable to retrieve tab url")
        when {
            url.endsWith("albums") -> {

            }
            url.endsWith("artists") -> {
                launch {
                    ArtistDiff.update()
                }
            }
        }
    }
}

object Timer {
    private var time = window.performance.now()

    private suspend fun await(): Double {
        val newTime = window.awaitAnimationFrame()
        val dt = newTime - time
        time = newTime
        return dt.coerceAtMost(200.0) // at most 200ms
    }

    suspend fun delay(ms: Int) {
        var dt = 0.0
        while (dt < ms) {
            dt += await()
        }
    }
}
