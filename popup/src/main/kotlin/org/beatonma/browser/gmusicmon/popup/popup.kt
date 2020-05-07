package org.beatonma.browser.gmusicmon.popup

import org.beatonma.browser.gmusicmon.common.*
import org.beatonma.browser.gmusicmon.dom.ktx.addOnClickListener
import org.beatonma.browser.gmusicmon.dom.ktx.getButton
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlin.browser.document
import kotlin.dom.clear

typealias ArtistName = String
typealias Artists = Set<ArtistName>

fun main() {
    setupListeners()
    loadSavedArtists()
}

private fun loadSavedArtists() {
    browser.loadLocal<Array<ArtistName>>(StorageKey.ARTIST_LIST) {
        onArtistsLoaded(it?.toSet())
    }
    browser.loadLocal<String>(StorageKey.LAST_UPDATED) {
        onTimeLoaded(it)
    }
}

private fun onTimeLoaded(date: String?) {
    val container = document.getElementById("last_update") ?: return
    container.clear()
    container.append {
        div {
            if (date == null) {
                +"No previous data"
            }
            else {
                +"Last update: $date"
            }
        }
    }

}

private fun onArtistsLoaded(artists: Artists?) {
    val container = document.getElementById("artists_container") ?: return
    container.clear()
    container.append {
        if (artists == null) {
            div { +"No saved artists" }
        }
        else {
            h1 { +"${artists.size} artists" }
            artists.forEach { artist ->
                div("artist-name") { +artist.replace("&amp;", "&") }
            }
        }
    }
}

private fun setupListeners() {
    document.getButton(Messages.UPDATE_ARTISTS)!!.addOnClickListener {
        browser.withActiveTab { tab ->
            tab.id?.also { sendUpdateArtistsMessage(it) }
        }
    }
}

private fun sendUpdateArtistsMessage(tabId: Int) {
    browser.sendCommand(tabId, Messages.UPDATE_ARTISTS)
}
