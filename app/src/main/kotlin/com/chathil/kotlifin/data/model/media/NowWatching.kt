package com.chathil.kotlifin.data.model.media

sealed class NowWatching(
    open val id: String,
    open val title: String,
    open val state: MediaState
) {
    data class Movie(
        override val id: String,
        override val title: String,
        override val state: MediaState
    ) : NowWatching(id, title, state)

    data class Show(
        override val id: String,
        override val title: String,
        override val state: MediaState,
        val seasonName: String,
    ) : NowWatching(id, title, state)
}