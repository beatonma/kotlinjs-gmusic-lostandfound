package com.chrome.platform

import com.chrome.platform.tabs.Tabs
import com.chrome.platform.runtime.Runtime

@JsName("chrome")
external object Chrome {
    val runtime: Runtime
    val tabs: Tabs
}