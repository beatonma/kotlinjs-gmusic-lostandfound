package org.beatonma.browser.gmusicmon.popup

import com.chrome.platform.Chrome
import com.chrome.platform.tabs.QueryInfo
import org.beatonma.browser.gmusicmon.common.Messages
import org.beatonma.browser.gmusicmon.dom.ktx.addOnClickListener
import org.beatonma.browser.gmusicmon.dom.ktx.getButton
import kotlin.browser.document

fun main() {
    document.write("POPUP SCRIPT!")
    setupListeners()
}

fun setupListeners() {
    document.getButton("update_artists_button")?.addOnClickListener {
        Chrome.tabs.query(object: QueryInfo {
            override val active: Boolean = true
            override val currentWindow: Boolean = true
        }) {
            it.firstOrNull()?.get("id")
        }
        sendUpdateArtistsMessage(0)
    }
}

private fun sendUpdateArtistsMessage(tabId: Int) {
    Chrome.tabs.sendMessage(tabId, jsObject { command = Messages.UPDATE_ARTISTS }) {
        // TODO
    }
}

inline fun jsObject(init: dynamic.() -> Unit): dynamic {
    val o = js("{}")
    init(o)
    return o
}