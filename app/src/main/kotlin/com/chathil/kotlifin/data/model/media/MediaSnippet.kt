package com.chathil.kotlifin.data.model.media

import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.show.Series

sealed class MediaSnippet(
    open val id: String,
    open val title: String,
    open val img: JellyfinImage
) {
    data class Movie(
        override val id: String,
        override val title: String,
        val state: MediaState,
        override val img: JellyfinImage,
    ) : MediaSnippet(id, title, img)

    data class Episode(
        override val id: String,
        override val title: String,
        val state: MediaState,
        override val img: JellyfinImage,
        val season: Int,
        val eps: Int,
        val epsTitle: String
    ) : MediaSnippet(id, title, img)

    data class Show(
        override val id: String,
        override val title: String,
        override val img: JellyfinImage,
        val series: List<Series>
    ) : MediaSnippet(id, title, img)

    data class Unknown(
        override val id: String = "",
        override val title: String = "",
        val state: MediaState = MediaState.Empty,
        override val img: JellyfinImage = JellyfinImage.Empty,
    ) : MediaSnippet(id, title, img)
}
