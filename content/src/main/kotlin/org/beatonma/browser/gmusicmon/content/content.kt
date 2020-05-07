package org.beatonma.browser.gmusicmon.content

import kotlinx.coroutines.*
import kotlinx.html.*
import kotlinx.html.dom.append
import org.beatonma.browser.gmusicmon.common.*
import org.w3c.dom.*
import kotlin.browser.document
import kotlin.browser.window
import kotlin.coroutines.CoroutineContext
import kotlin.dom.clear
import kotlin.dom.createElement
import kotlin.js.Date


typealias ArtistName = String
typealias Artists = Set<ArtistName>

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

    private val timer = Timer()

    private var savedArtists: Artists = setOf()

    init {
        loadSavedArtists()
    }

    private fun loadSavedArtists() {
        browser.loadLocal<Array<ArtistName>>(StorageKey.ARTIST_LIST) {
            onArtistsLoaded(it?.toSet())
        }
    }

    private fun saveArtists(artists: Artists) {
        browser.saveLocal(StorageKey.ARTIST_LIST, artists.toTypedArray())
        browser.saveLocal(StorageKey.LAST_UPDATED,  Date().toTimeString())
    }

    private fun onArtistsLoaded(artists: Artists?) {
        console.log("Loaded ${artists?.size ?: 0} artists from storage")
        savedArtists = artists ?: setOf()
    }

    fun update() {
        launch {
            val artists = getArtists()
            checkForChanges(savedArtists, artists)
        }
    }

    private fun checkForChanges(oldArtists: Artists, newArtists: Artists) {
        val added = newArtists.filterNot { oldArtists.contains(it) }.toSet()
        val removed = oldArtists.filterNot { newArtists.contains(it) }.toSet()

        if (added.isEmpty() && removed.isEmpty()) {
            showNoChangesUi()
        }
        else {
            showComparisonUi(added, removed)
        }
    }

    private fun getUiContainer(): Element {
        return document.getElementById("gmusic2-update")?.also { it.clear() }
            ?: document.createElement("div") {
                id = "gmusic2-update"
                classList.add("gmusic2-update")
            }.also { document.body?.append(it) }
    }

    private fun showNoChangesUi() {
        getUiContainer().append {
            div { +"No changes :)" }
        }
    }

    private fun showComparisonUi(added: Artists, removed: Artists) {
        getUiContainer().append {
            div("gmusic2-header") {
                button { +"close button" }
            }
            div("gmusic2-comparison") {
                div("column") {
                    h1 { +"Added artists" }
                    artistListUi(added)
                }
                div("column") {
                    h1 { +"Removed artists" }
                    artistListUi(removed)
                }
            }
        }
    }

    private fun TagConsumer<HTMLElement>.artistListUi(artists: Artists): List<HTMLElement> =
        artists.map { name -> div { +name } }

    private fun showUpdateMessageUi(data: UiData) {
        val message = when (data.updateComplete) {
            true -> "Update complete! Found ${data.artistCount} artists."
            false -> "${data.artistCount} artists discovered..."
        }
        getUiContainer().append {
            div("gmusic2-update-message") {
                id = "gmusic2-update-message"
                +message
            }
        }
    }

    private suspend fun getArtists(outSet: MutableSet<ArtistName> = mutableSetOf()): Artists {
        val mainContainer = document.getElementById("mainContainer") ?: throw Exception("Artists container not found!")
        while (mainContainer.scrollTop != 0.0) {
            mainContainer.scrollTo(0.0, 0.0)
        }
        var scrollPositionY = -1.0
        while (mainContainer.scrollTop > scrollPositionY) {
            scrollPositionY = mainContainer.scrollTop
            mainContainer.scrollBy(0.0, window.innerHeight / 2.0)
            outSet.addAll(getVisibleArtists())

            showUpdateMessageUi(UiData(artistCount = outSet.size))
            timer.delay(200)
        }

        showUpdateMessageUi(UiData(artistCount = outSet.size, updateComplete = true))

        return outSet.also { saveArtists(it) }
    }

    private suspend fun getVisibleArtists(): List<ArtistName> {
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


private data class UiData(
    var updateComplete: Boolean = false,
    var artistCount: Int = 0
)