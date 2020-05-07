package com.chrome.platform

import org.beatonma.browser.tabs.Tabs
import org.beatonma.browser.runtime.Runtime
import org.beatonma.browser.Browser
import org.beatonma.browser.storage.Storage

/**
 * https://developer.chrome.com/extensions/api_index
 * */
@JsName("chrome")
external object Chrome : Browser {
    // https://developer.chrome.com/extensions/runtime
    override val runtime: Runtime

    // https://developer.chrome.com/extensions/storage
    override val storage: Storage

    // https://developer.chrome.com/extensions/tabs
    override val tabs: Tabs
}