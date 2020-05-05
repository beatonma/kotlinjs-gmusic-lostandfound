package org.beatonma.browser.gmusicmon.dom.ktx

import org.w3c.dom.Document
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import kotlin.browser.document

fun Document.getButton(id: String): HTMLButtonElement? = document.getElementById(id) as? HTMLButtonElement

inline fun <T: HTMLElement> T.addOnClickListener(
    crossinline callback: (Event) -> Unit
) {
    addEventListener(
        "click",
        callback = object : EventListener {
            override fun handleEvent(event: Event) {
                callback(event)
            }
        },
        options = null
    )
}