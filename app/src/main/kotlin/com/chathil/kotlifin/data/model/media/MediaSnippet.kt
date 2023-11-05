package com.chathil.kotlifin.data.model.media

import com.chathil.kotlifin.data.model.image.JellyfinImage

sealed class MediaSnippet(
    open val id: String,
    open val title: String,
    open val img: JellyfinImage,
    open val state: MediaState
) {
    data class Movie(
        override val id: String,
        override val title: String,
        override val state: MediaState,
        override val img: JellyfinImage,
    ) : MediaSnippet(id, title, img, state)

    data class Show(
        override val id: String,
        override val title: String,
        override val state: MediaState,
        override val img: JellyfinImage,
        val season: Int,
        val eps: Int,
        val epsTitle: String
    ) : MediaSnippet(id, title, img, state)

    data class Unknown(
        override val id: String = "",
        override val title: String = "",
        override val state: MediaState = MediaState.Empty,
        override val img: JellyfinImage = JellyfinImage.Empty,
    ) : MediaSnippet(id, title, img, state)
}
