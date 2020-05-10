package org.beatonma.browser.gmusicmon.content

typealias ArtistName = String
typealias Artists = Set<ArtistName>

typealias AlbumName = String
typealias Albums = Set<Album>

data class Album(val artistName: ArtistName, val albumName: AlbumName) {
    fun serialize(): String = "$artistName$SEPARATOR$albumName"

    companion object {
        private const val SEPARATOR = ";_"

        fun deserialize(serialized: String): Album {
            val (artistName, albumName) = serialized.split(SEPARATOR)
            return Album(artistName = artistName, albumName = albumName)
        }
    }
}
