package org.beatonma.browser.gmusicmon.content

import org.w3c.dom.*
import kotlin.browser.document

private var artists = listOf<String>()

fun main() {
    console.log("CONTENT SCRIPT!")
    document.getElementById("material-player-left-wrapper")?.addEventListener("click", { updateArtists() })
}

fun updateArtists() {
    console.log("updating org.beatonma.browser.gmusicmon.content.getArtists list")
    artists =
        getArtists()
    console.log("org.beatonma.browser.gmusicmon.content.getArtists: $artists")
}

fun getArtists(): List<String> {
    val r: HTMLCollection = document.getElementsByClassName("material-card entity-card")
    return r.asList()
//        .filter { it.hasAttribute("data-type") }
        .filter { it.getAttribute("data-type") == "artist" }
        .mapNotNull { it.getElementsByTagName("a")[0]?.innerHTML }
}

fun Document.getDiv(id: String): HTMLDivElement? = getElementById(id) as? HTMLDivElement
