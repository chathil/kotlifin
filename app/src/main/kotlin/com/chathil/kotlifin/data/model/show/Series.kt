package com.chathil.kotlifin.data.model.show

import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet

data class Series(
    val id: String,
    val img: JellyfinImage,
    val eps: List<MediaSnippet.Episode>
) {
    companion object {
        val Empty = Series(id = "", img = JellyfinImage.Empty, eps = emptyList())
    }
}
