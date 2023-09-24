package com.chathil.kotlifin.data.dto.extension

import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_ASPECT_RATIO
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_POSTER_SIZE
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.ImageType

fun BaseItemDto.asMediaSnippet(baseUrl: String): MediaSnippet {
    return MediaSnippet(
        id = id.toString(),
        title = name ?: "",
        posterImg = JellyfinImage(
            baseUrl = baseUrl,
            itemId = id.toString(),
            imageTags?.getOrDefault(ImageType.PRIMARY, "") ?: "",
            ImageType.PRIMARY
        )
    )
}
