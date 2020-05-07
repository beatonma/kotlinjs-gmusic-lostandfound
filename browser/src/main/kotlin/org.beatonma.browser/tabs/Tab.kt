package org.beatonma.browser.tabs

/**
 * https://developer.chrome.com/extensions/tabs#type-Tab
 */
external interface Tab {
    val id: Int?
    val index: Int
    val windowId: Int?
    val openerTabId: Int?
    val highlighted: Boolean
    val active: Boolean
    val pinned: Boolean
    val lastAccessed: Int?
    val audible: Boolean?
    val mutedInfo: MutedInfo?
    val url: String?
    val title: String?
    val favIconUrl: String?
    val status: String?
    val discarded: Boolean?
    val incognito: Boolean?
    val width: Int?
    val height: Int?
    val hidden: Boolean?
    val sessionId: String?
    val cookieStoreId: String?
    val isArticle: Boolean?
    val isInReaderMode: Boolean?
    val sharingState: SharingState?
    val attention: Boolean?
    val successorTabId: Int?
}

typealias MutedInfoReason = String
external interface MutedInfo {
    val muted: Boolean
    val reason: MutedInfoReason?
    val extensionId: String?
}

external interface SharingState {
    val screen: String?
    val camera: Boolean
    val microphone: Boolean
}