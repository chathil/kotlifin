package com.chathil.kotlifin.data.model.show

import com.chathil.kotlifin.data.model.image.JellyfinImage

data class ShowNextUp(
    val id: String,
    val title: String,
    val img: JellyfinImage,
    val season: Int,
    val eps: Int,
    val epsTitle: String
)
