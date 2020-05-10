package org.beatonma.browser.gmusicmon.content.artists

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import org.beatonma.browser.gmusicmon.common.browser
import org.beatonma.browser.gmusicmon.common.getResourceUrl
import org.beatonma.browser.gmusicmon.content.Elements
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.dom.clear
import kotlin.dom.createElement

object ArtistUi {
    private fun close() {
        document.getElementById(Elements.MAIN)?.remove()
    }

    private fun getContainer(): Element {
        return document.getElementById(Elements.MAIN)?.also { it.clear() }
            ?: document.createElement("div") {
                id = Elements.MAIN
                classList.add(Elements.MAIN)
            }.also { document.body?.append(it) }
    }

    private fun showNoChanges() {
        getContainer().append {
            div(Elements.MESSAGE) {
                id = Elements.MESSAGE
                +"No changes :)"
            }
        }
    }

    fun showComparison(added: Artists, removed: Artists) {
        if (added.isEmpty() && removed.isEmpty()) {
            showNoChanges()
            return
        }
        getContainer().append {
            div(Elements.HEADER) {
                div { }
                div(Elements.ACTIONS) {
                    img(alt = "Close", classes = Elements.CLOSE_BUTTON, src = browser.getResourceUrl("ic_close.svg")) {
                        onClickFunction = { close() }
                    }
                }
            }
            div(Elements.COMPARISON) {
                div(Elements.COLUMN) {
                    h1 { +"Added artists" }
                    artistList(added, Elements.ADDED)
                }
                div(Elements.COLUMN) {
                    h1 { +"Removed artists" }
                    artistList(removed, Elements.REMOVED)
                }
            }
        }
    }

    private fun TagConsumer<HTMLElement>.artistList(artists: Artists, className: String): List<HTMLElement> =
        artists.map { name -> artistListItem(name, className) }

    private fun TagConsumer<HTMLElement>.artistListItem(name: ArtistName, className: String): HTMLElement =
        div(Elements.ARTIST) {
            span(className) {
                when (className) {
                    Elements.ADDED -> +"+"
                    Elements.REMOVED -> +"-"
                }
            }
            span { +name }
        }

    fun showUpdateMessage(data: ArtistUiData) {
        val message = when (data.updateComplete) {
            true -> "Update complete! Found ${data.artistCount} artists."
            false -> "${data.artistCount} artists discovered..."
        }
        getContainer().append {
            div(Elements.MESSAGE) {
                id = Elements.MESSAGE
                +message
            }
        }
    }
}


data class ArtistUiData(
    var updateComplete: Boolean = false,
    var artistCount: Int = 0
)