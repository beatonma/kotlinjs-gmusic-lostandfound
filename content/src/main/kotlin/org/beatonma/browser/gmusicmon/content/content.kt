package org.beatonma.browser.gmusicmon.content

import com.chrome.platform.Chrome
import kotlinx.coroutines.*
import org.beatonma.browser.gmusicmon.common.Messages
import org.w3c.dom.*
import kotlin.browser.document
import kotlin.browser.window
import kotlin.coroutines.CoroutineContext
import kotlin.dom.createElement


private val application = Application()

fun main() {
    console.log("CONTENT SCRIPT!")
    Chrome.runtime.onMessage.addListener { message, _, _ ->
        when (message["command"]) {
            Messages.UPDATE_ARTISTS -> application.update()
            else -> console.log("Unhandled message: ${message.stringify()}")
        }
    }
}

fun Any?.stringify() = JSON.stringify(this)

class Application : CoroutineScope {
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    private val timer = Timer()

    var artists = setOf<String>()

    fun update() {
        launch {
            artists = getArtists()
        }
    }

    private fun showUpdateMessage(artistCount: Int, updateComplete: Boolean = false) {
        val messageContainer = document.getElementById("gmusic2-updating-message") ?: document.createElement("div") {
            id = "gmusic2-updating-message"
            classList.add("gmusic2-updating-message")
        }

        val message = when (updateComplete) {
            true -> "Update complete! Found $artistCount artists."
            false -> "$artistCount artists discovered..."
        }
        messageContainer.innerHTML = message
        document.body?.append(messageContainer)
    }

    private suspend fun getArtists(outSet: MutableSet<String> = mutableSetOf()): Set<String> {
        val mainContainer = document.getElementById("mainContainer") ?: throw Exception("Artists container not found!")
        while (mainContainer.scrollTop != 0.0) {
            mainContainer.scrollTo(0.0, 0.0)
        }
        var scrollPositionY = -1.0
        while (mainContainer.scrollTop > scrollPositionY) {
            scrollPositionY = mainContainer.scrollTop
            mainContainer.scrollBy(0.0, window.innerHeight / 2.0)
            outSet.addAll(getVisibleArtists())
            showUpdateMessage(outSet.size)
            timer.delay(200)
        }
        showUpdateMessage(outSet.size, updateComplete = true)
        return outSet
    }

    private suspend fun getVisibleArtists(): List<String> {
        val r: HTMLCollection = document.getElementsByClassName("material-card entity-card")
        return r.asList()
            .filter { it.getAttribute("data-type") == "artist" }
            .mapNotNull { it.getElementsByTagName("a")[0]?.innerHTML }
    }
}


class Timer {
    private var time = window.performance.now()

    private suspend fun await(): Double {
        val newTime = window.awaitAnimationFrame()
        val dt = newTime - time
        time = newTime
        return dt.coerceAtMost(200.0) // at most 200ms
    }

    suspend fun delay(ms: Int) {
        console.log("delaying for ${ms}ms...")
        var dt = 0.0
        while (dt < ms) {
            dt += await()
        }
    }
}