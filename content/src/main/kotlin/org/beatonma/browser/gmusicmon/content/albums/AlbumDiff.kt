package org.beatonma.browser.gmusicmon.content.albums

import org.beatonma.browser.gmusicmon.common.StorageKey
import org.beatonma.browser.gmusicmon.common.browser
import org.beatonma.browser.gmusicmon.common.loadLocal
import org.beatonma.browser.gmusicmon.common.saveLocal
import org.beatonma.browser.gmusicmon.content.*
import org.w3c.dom.*
import kotlin.browser.document

object AlbumDiff : GmusicDiffer<Album> {
    override var saved: Albums = setOf()

    override fun loadSaved() {
        browser.loadLocal<Array<String>>(StorageKey.ALBUM_LIST) { serialized ->
            saved = serialized?.map { Album.deserialize(it) }?.toSet() ?: setOf()
        }
    }

    override fun checkForChanges(old: Albums, new: Albums) {
        val added = new.filterNot { old.contains(it) }.toSet()
        val removed = old.filterNot { new.contains(it) }.toSet()

        AlbumUi.showComparison(added, removed)
    }

    override suspend fun readFromScreen(): Albums {
        return document.getElementsByClassName("material-card")
            .asList()
            .filter { it.getAttribute("data-type") == "album" }
            .mapNotNull { getAlbum(it) }
            .toSet()
    }

    private fun getAlbum(element: Element): Album? {
        val albumName = element.getElementsByClassName("title")[0]?.innerHTML ?: return null
        val artistName = element.getElementsByClassName("sub-title")[0]?.innerHTML ?: return null
        return Album(artistName = artistName, albumName = albumName)
    }

    override fun onUpdateReadComplete(data: Albums) {
        browser.saveLocal(StorageKey.ALBUM_LIST, data.map { it.serialize() }.toTypedArray())
    }

    override fun updateUi(data: Albums, complete: Boolean) {
        AlbumUi.showUpdateMessage(data.size, complete)
    }
}