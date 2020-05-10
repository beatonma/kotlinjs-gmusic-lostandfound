package org.beatonma.browser.gmusicmon.content.artists

import kotlinx.html.*
import org.beatonma.browser.gmusicmon.content.*
import org.w3c.dom.HTMLElement


object ArtistUi : BaseUi<ArtistName> {
    override val columnTitles: ColumnTitles
        get() = ColumnTitles("Added artists", "Removed artists")
    override val updateMessages: UpdateMessages
        get() = object: UpdateMessages {
            override fun updateCompleteMessage(itemCount: Int) =
                "Update complete! Found $itemCount artists."

            override fun updateOngoingMessage(itemCount: Int) =
                "$itemCount artists discovered..."
        }

    override fun TagConsumer<HTMLElement>.listItem(item: ArtistName): HTMLElement = span { +item }
}
