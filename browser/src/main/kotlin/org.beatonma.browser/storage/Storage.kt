package org.beatonma.browser.storage

import kotlin.js.Json

external interface Storage {
    val sync: Store
    val local: Store
}

external interface Store {
    fun <T> set(obj: T, callback: (() -> Unit)?)
    fun <T> get(key: String, callback: (T) -> Unit)
//    fun get(key: String, callback: (Json) -> Unit)
//    fun get(key: Array<String>, callback: (Json) -> Unit)
}