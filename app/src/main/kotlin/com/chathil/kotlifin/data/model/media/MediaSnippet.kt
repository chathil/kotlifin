package com.chathil.kotlifin.data.model.media

import com.chathil.kotlifin.data.model.image.JellyfinImage

data class MediaSnippet(
    val id: String,
    val title: String,
    val posterImg: JellyfinImage
)
