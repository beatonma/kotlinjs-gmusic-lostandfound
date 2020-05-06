package com.chrome.platform.tabs

import kotlin.js.Json

/**
 * https://developer.chrome.com/extensions/tabs
 * */
external interface Tabs {
    // https://developer.chrome.com/extensions/tabs#method-sendMessage
    fun sendMessage(tabId: Int, message: Any, options: Json?, callback: ((Json) -> Unit)?)

    // https://developer.chrome.com/extensions/tabs#method-query
    fun query(queryInfo: QueryInfo, callback: (Array<Json>) -> Unit)
}


data class QueryInfo(
    val active: Boolean,
    val currentWindow: Boolean,
)