package org.beatonma.browser

import org.beatonma.browser.runtime.Runtime
import org.beatonma.browser.storage.Storage
import org.beatonma.browser.tabs.Tabs

external interface Browser {
    val runtime: Runtime
    val storage: Storage
    val tabs: Tabs
}