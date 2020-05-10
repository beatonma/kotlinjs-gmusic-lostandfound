package org.beatonma.browser.runtime

import org.beatonma.browser.tabs.Tab
import kotlin.js.Json

/**
 * https://developer.chrome.com/extensions/runtime
 */
external interface Runtime {
    val onMessage: OnMessage
    fun getURL(path: String): String
}

/**
 * https://developer.chrome.com/extensions/runtime#event-onMessage
 */
external interface OnMessage {
    fun addListener(
        listener: (
            Json,
            MessageSender,
            () -> Boolean?,
        ) -> Unit
    )
}


/**
 * https://developer.chrome.com/extensions/runtime#type-MessageSender
 */
external interface MessageSender {
    val tab: Tab?
    val frameId: Int?
    val id: String?
    val nativeApplication: String?
    val tlsChannelId: String?
    val origin: String?
}
