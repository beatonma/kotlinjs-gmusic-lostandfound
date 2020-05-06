package org.beatonma.browser.gmusicmon.popup

import com.chrome.platform.Chrome
import com.chrome.platform.tabs.QueryInfo
import org.beatonma.browser.gmusicmon.common.Messages
import org.beatonma.browser.gmusicmon.dom.ktx.addOnClickListener
import org.beatonma.browser.gmusicmon.dom.ktx.getButton
import kotlin.browser.document
import kotlin.js.json

fun main() {
    setupListeners()
}

fun setupListeners() {
    document.getButton(Messages.UPDATE_ARTISTS)!!.addOnClickListener {
        Chrome.tabs.query(QueryInfo(active = true, currentWindow = true)) {
            val tabId = it.firstOrNull()?.get("id")?.toString()?.toIntOrNull()
            if (tabId == null) {
                console.error("tabId is null! $it")
                return@query
            }
            sendUpdateArtistsMessage(tabId)
        }
    }
}

private fun sendUpdateArtistsMessage(tabId: Int) {
    Chrome.tabs.sendMessage(
        tabId,
        json("command" to Messages.UPDATE_ARTISTS),
        options = null,
        callback = null
    )
}
