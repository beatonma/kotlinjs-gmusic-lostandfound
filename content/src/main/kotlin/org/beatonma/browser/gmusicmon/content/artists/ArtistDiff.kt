package org.beatonma.browser.gmusicmon.content.artists

import org.beatonma.browser.gmusicmon.common.StorageKey
import org.beatonma.browser.gmusicmon.common.browser
import org.beatonma.browser.gmusicmon.common.loadLocal
import org.beatonma.browser.gmusicmon.common.saveLocal
import org.beatonma.browser.gmusicmon.content.GmusicDiffer
import org.w3c.dom.HTMLCollection
import org.w3c.dom.asList
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.js.Date


typealias ArtistName = String
typealias Artists = Set<ArtistName>

object ArtistDiff: GmusicDiffer<ArtistName> {

    override var saved: Artists = setOf()

    override fun loadSaved() {
        browser.loadLocal<Array<ArtistName>>(StorageKey.ARTIST_LIST) {
            saved = it?.toSet() ?: setOf()
        }
    }

    override fun checkForChanges(old: Artists, new: Artists) {
        val added = new.filterNot { old.contains(it) }.toSet()
        val removed = old.filterNot { new.contains(it) }.toSet()

        ArtistUi.showComparison(added, removed)
    }

    override suspend fun readFromScreen(): Artists {
        val r: HTMLCollection = document.getElementsByClassName("material-card entity-card")
        return r.asList()
            .filter { it.getAttribute("data-type") == "artist" }
            .mapNotNull { it.getElementsByTagName("a")[0]?.innerHTML }
            .toSet()
    }

    override fun updateUi(data: Artists, complete: Boolean) {
        ArtistUi.showUpdateMessage(
            ArtistUiData(artistCount = data.size, updateComplete = complete)
        )
    }

    override fun onUpdateReadComplete(data: Set<ArtistName>) {
        browser.saveLocal(StorageKey.ARTIST_LIST, data.toTypedArray())
        browser.saveLocal(StorageKey.LAST_UPDATED,  Date().toTimeString())
    }
}
