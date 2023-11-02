package com.chathil.kotlifin.data.dto.extension

import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.media.MediaState
import com.chathil.kotlifin.data.model.media.NowWatching
import com.chathil.kotlifin.data.model.show.ShowNextUp
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_ASPECT_RATIO
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_POSTER_SIZE
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.ImageType
import timber.log.Timber

fun BaseItemDto.asMediaSnippet(baseUrl: String): MediaSnippet {
    return MediaSnippet(
        id = id.toString(),
        title = name ?: "",
        posterImg = JellyfinImage(
            baseUrl = baseUrl,
            itemId = id.toString(),
            imageTag = imageTags?.getOrDefault(ImageType.PRIMARY, "") ?: "",
            imageType = ImageType.PRIMARY
        )
    )
}

fun BaseItemDto.asNowWatching(baseUrl: String): NowWatching {
    return when (type) {
        BaseItemKind.MOVIE -> {
            NowWatching.Movie(
                id = id.toString(),
                title = name ?: "",
                state = MediaState(
                    isFavorite = userData?.isFavorite ?: false,
                    isPlayed = userData?.played ?: false
                ),
                img = JellyfinImage(
                    baseUrl = baseUrl,
                    itemId = id.toString(),
                    imageTag = parentBackdropImageTags?.get(0) ?: "",
                    imageType = ImageType.BACKDROP
                )
            )
        }

        BaseItemKind.EPISODE -> {
            NowWatching.Show(
                id = id.toString(),
                title = seriesName ?: "",
                state = MediaState(
                    isFavorite = userData?.isFavorite ?: false,
                    isPlayed = userData?.played ?: false
                ),
                img = JellyfinImage(
                    baseUrl = baseUrl,
                    itemId = parentBackdropItemId.toString(),
                    imageTag = parentBackdropImageTags?.get(0) ?: "",
                    imageType = ImageType.BACKDROP
                ),
                season = parentIndexNumber ?: 0,
                eps = indexNumber ?: 0,
                epsTitle = name ?: ""

            )
        }

        else -> {
            NowWatching.Unknown(
                id = id.toString(),
                title = name ?: "",
                state = MediaState(
                    isFavorite = userData?.isFavorite ?: false,
                    isPlayed = userData?.played ?: false
                ),
                img = JellyfinImage(
                    baseUrl = baseUrl,
                    itemId = parentBackdropItemId.toString(),
                    imageTag = parentBackdropImageTags?.get(0) ?: "",
                    imageType = ImageType.BACKDROP
                )
            )
        }
    }
}

fun BaseItemDto.asShowNextUp(baseUrl: String): ShowNextUp {
    return ShowNextUp(
        id = id.toString(),
        title = seriesName ?: "",
        img = JellyfinImage(
            baseUrl = baseUrl,
            itemId = parentBackdropItemId.toString(),
            imageTag = parentBackdropImageTags?.get(0) ?: "",
            imageType = ImageType.BACKDROP
        ),
        season = parentIndexNumber ?: 0,
        eps = indexNumber ?: 0,
        epsTitle = name ?: ""
    )
}
