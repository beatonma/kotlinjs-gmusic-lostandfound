package org.beatonma.browser.gmusicmon.content.albums

import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import kotlinx.html.span
import org.beatonma.browser.gmusicmon.content.*
import org.w3c.dom.HTMLElement

object AlbumUi: BaseUi<Album> {
    override val columnTitles: ColumnTitles
        get() = ColumnTitles("Added albums", "Removed albums")
    override val updateMessages: UpdateMessages
        get() = object: UpdateMessages {
            override fun updateCompleteMessage(itemCount: Int) =
                "Update complete! Found $itemCount albums."

            override fun updateOngoingMessage(itemCount: Int) =
                "$itemCount albums discovered..."
        }

    override fun TagConsumer<HTMLElement>.listItem(item: Album): HTMLElement = div(Elements.ALBUM) {
        span(Elements.ALBUM_NAME) { +item.albumName }
        span(Elements.INLINE) { +"by"}
        span(Elements.ALBUM_ARTIST) { +item.artistName }
    }
}