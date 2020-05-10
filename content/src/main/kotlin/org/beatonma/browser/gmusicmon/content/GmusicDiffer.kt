package org.beatonma.browser.gmusicmon.content

import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.browser.window

interface GmusicDiffer<T> {
    var saved: Set<T>

    fun loadSaved()

    fun checkForChanges(old: Set<T>, new: Set<T>)

    suspend fun update(outSet: MutableSet<T> = mutableSetOf()) {
        loadSaved()

        val mainContainer = document.getElementById("mainContainer")
            ?: throw Exception("GMUSIC container not found!")


        mainContainer.stepScrollableContent {
            outSet.addAll(readFromScreen())
            updateUi(outSet, complete = false)
        }

        updateUi(outSet, complete = true)

        onUpdateReadComplete(outSet)

        checkForChanges(saved, outSet)
    }

    /**
     * Scroll from top to bottom in small steps, running the lambda at each step
     */
    suspend fun Element.stepScrollableContent(onEachStep: suspend () -> Unit) {
        scrollTo(0.0, 0.0)
        Timer.delay(500)

        var scrollPositionY = -1.0
        while (scrollTop > scrollPositionY) {
            scrollPositionY = scrollTop
            scrollBy(0.0, window.innerHeight / 2.0)
            Timer.delay(200)
            onEachStep()
        }
    }

    suspend fun readFromScreen(): Set<T>
    fun onUpdateReadComplete(data: Set<T>)

    fun updateUi(data: Set<T>, complete: Boolean)
}