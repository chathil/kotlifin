package com.chathil.kotlifin.data.model.media

data class MediaState(val isFavorite: Boolean, val isPlayed: Boolean) {
    companion object {
        val Empty = MediaState(isFavorite = false, isPlayed = false)
    }
}
