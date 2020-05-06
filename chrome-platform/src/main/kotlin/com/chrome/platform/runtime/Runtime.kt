package com.chrome.platform.runtime

import com.chrome.platform.tabs.Tab
import kotlin.js.Json

/**
 * https://developer.chrome.com/extensions/runtime
 */
external interface Runtime {
    val onMessage: OnMessage
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
data class MessageSender(
    val tab: Tab? = null,
    val frameId: Int? = null,
    val id: String? = null,
    val nativeApplication: String? = null,
    val tlsChannelId: String? = null,
    val origin: String? = null,
)