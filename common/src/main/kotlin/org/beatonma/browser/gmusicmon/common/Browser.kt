package org.beatonma.browser.gmusicmon.common

import com.chrome.platform.Chrome
import org.beatonma.browser.Browser
import org.beatonma.browser.tabs.QueryInfo
import org.beatonma.browser.tabs.Tab
import kotlin.js.Json
import kotlin.js.json

private object BrowserMessage {
    const val COMMAND = "command"
}

val browser: Browser = Chrome


fun Browser.sendCommand(tabId: Int, command: String) {
    tabs.sendMessage(
        tabId,
        json(BrowserMessage.COMMAND to command),
        options = null,
        callback = null,
    )
}

fun Browser.onCommand(command: String, callback: () -> Unit) {
    runtime.onMessage.addListener { message, _, _ ->
        when (message[BrowserMessage.COMMAND]) {
            command -> callback()
            else -> console.log("Unhandled command: ${command.stringify()}")
        }
    }
}

inline fun Browser.withActiveTab(crossinline block: (tab: Tab) -> Unit) {
    tabs.query(QueryInfo(active = true, currentWindow = true)) {
        val tab = it.firstOrNull()

        if (tab == null) {
            console.error("Unable to retrieve active tab")
        }
        else {
            block(tab)
        }
    }
}

private fun <T> Browser.saveLocal(content: T, callback: (() -> Unit)? = null) {
    storage.local.set(content, callback)
}

fun Browser.saveLocal(key: String, content: Array<String>, callback: (() -> Unit)? = null) {
    saveLocal(
        json(key to content),
        callback
    )
}

fun Browser.saveLocal(key: String, content: String, callback: (() -> Unit)? = null) {
    saveLocal(
        json(key to content),
        callback
    )
}

fun <T> Browser.loadLocal(key: String, then: (T?) -> Unit) {
    storage.local.get<Json>(key) {
        then(it[key] as T?)
    }
}

fun Browser.getResourceUrl(path: String): String = runtime.getURL(path)