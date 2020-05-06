package com.chrome.platform.tabs

/**
 * https://developer.chrome.com/extensions/tabs#type-Tab
 */
class Tab(
    var id: Int? = null,
    var index: Int,
    var windowId: Int? = null,
    var openerTabId: Int? = null,
    var highlighted: Boolean,
    var active: Boolean,
    var pinned: Boolean,
    var lastAccessed: Int? = null,
    var audible: Boolean? = null,
    var mutedInfo: MutedInfo? = null,
    var url: String? = null,
    var title: String? = null,
    var favIconUrl: String? = null,
    var status: String? = null,
    var discarded: Boolean? = null,
    var incognito: Boolean? = null,
    var width: Int? = null,
    var height: Int? = null,
    var hidden: Boolean? = null,
    var sessionId: String? = null,
    var cookieStoreId: String? = null,
    var isArticle: Boolean? = null,
    var isInReaderMode: Boolean? = null,
    var sharingState: SharingState? = null,
    var attention: Boolean? = null,
    var successorTabId: Int? = null
)

typealias MutedInfoReason = String
data class MutedInfo(
    var muted: Boolean,
    var reason: MutedInfoReason? = null,
    var extensionId: String? = null
)

data class SharingState(
    var screen: String? = null,
    var camera: Boolean,
    var microphone: Boolean
)