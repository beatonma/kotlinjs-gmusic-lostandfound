package org.beatonma.browser.gmusicmon.content

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import org.beatonma.browser.gmusicmon.common.browser
import org.beatonma.browser.gmusicmon.common.getResourceUrl
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.dom.clear
import kotlin.dom.createElement

interface BaseUi<T> {
    val columnTitles: ColumnTitles
    val updateMessages: UpdateMessages

    fun close() {
        document.getElementById(Elements.MAIN)?.remove()
    }

    fun getContainer(): Element {
        return document.getElementById(Elements.MAIN)?.also { it.clear() }
            ?: document.createElement("div") {
                id = Elements.MAIN
                classList.add(Elements.MAIN)
            }.also { document.body?.append(it) }
    }

    private fun showNoChanges() {
        getContainer().append {
            div(Elements.MESSAGE) {
                id = Elements.MESSAGE
                +"No changes :)"
            }
        }
    }

    fun showComparison(
        added: Set<T>,
        removed: Set<T>
    ) {
        if (added.isEmpty() && removed.isEmpty()) {
            showNoChanges()
            return
        }
        getContainer().append {
            div(Elements.HEADER) {
                div { }
                div(Elements.ACTIONS) {
                    img(alt = "Close", classes = Elements.CLOSE_BUTTON, src = browser.getResourceUrl("ic_close.svg")) {
                        onClickFunction = { close() }
                    }
                }
            }
            div(Elements.COMPARISON) {
                div(Elements.COLUMN) {
                    h1 { +columnTitles.added }
                    div(Elements.COLUMN_CONTENT) {
                        addedList(added)
                    }
                }
                div(Elements.COLUMN) {
                    h1 { +columnTitles.removed }
                    div(Elements.COLUMN_CONTENT) {
                        removedList(removed)
                    }
                }
            }
        }
    }

    private fun TagConsumer<HTMLElement>.addedList(added: Set<T>) =
        if (added.isEmpty()) {
            div { +"None!" }
        }
        else {
            added.map { item -> wrappedListItem(item, Elements.ADDED) }
        }

    private fun TagConsumer<HTMLElement>.removedList(removed: Set<T>) =
        if (removed.isEmpty()) {
            div { +"None!" }
        }
        else {
            removed.map { item -> wrappedListItem(item, Elements.REMOVED) }
        }

    private fun TagConsumer<HTMLElement>.wrappedListItem(item: T, className: String): HTMLElement =
        div(Elements.ITEM) {
            span(className) {
                when (className) {
                    Elements.ADDED -> +"+"
                    Elements.REMOVED -> +"-"
                }
            }
            listItem(item)
        }

    fun TagConsumer<HTMLElement>.listItem(item: T): HTMLElement

    fun showUpdateMessage(itemCount: Int, updateComplete: Boolean) {
        getContainer().append {
            div(Elements.MESSAGE) {
                id = Elements.MESSAGE
                +updateMessages.getMessage(itemCount, updateComplete)
            }
        }
    }

}

data class ColumnTitles(val added: String, val removed: String)
interface UpdateMessages {
    fun updateCompleteMessage(itemCount: Int): String
    fun updateOngoingMessage(itemCount: Int): String

    fun getMessage(itemCount: Int, updateComplete: Boolean) = when (updateComplete) {
        true -> updateCompleteMessage(itemCount)
        false -> updateOngoingMessage(itemCount)
    }
}