package com.chathil.kotlifin.data.dto.extension

import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.media.MediaState
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.ImageType

fun BaseItemDto.asMediaSnippet(
    baseUrl: String,
    imageType: ImageType = ImageType.PRIMARY
): MediaSnippet {
    return when (type) {
        BaseItemKind.MOVIE -> asMovieMediaSnippet(baseUrl, imageType)

        BaseItemKind.EPISODE-> asEpisodeMediaSnippet(baseUrl, imageType)

        BaseItemKind.SERIES -> asShowMediaSnippet(baseUrl, imageType)

        else -> {
            MediaSnippet.Unknown(
                id = id.toString(),
                title = name ?: "",
                state = MediaState(
                    isFavorite = userData?.isFavorite ?: false,
                    isPlayed = userData?.played ?: false
                ),
                img = when (imageType) {
                    ImageType.PRIMARY -> asPrimaryImage(baseUrl)
                    ImageType.BACKDROP -> asBackDropImg(baseUrl)
                    else -> asPrimaryImage(baseUrl)
                }
            )
        }
    }
}

private fun BaseItemDto.asBackDropImg(baseUrl: String): JellyfinImage {
    return JellyfinImage(
        baseUrl = baseUrl,
        itemId = when (type) {
            BaseItemKind.MOVIE -> id.toString()
            BaseItemKind.EPISODE -> parentBackdropItemId.toString()
            else -> id.toString().ifBlank { parentBackdropItemId.toString() }
        },
        imageTag = parentBackdropImageTags?.get(0) ?: "",
        imageType = ImageType.BACKDROP
    )
}

private fun BaseItemDto.asPrimaryImage(baseUrl: String): JellyfinImage {
    return JellyfinImage(
        baseUrl = baseUrl,
        itemId = id.toString(),
        imageTag = imageTags?.getOrDefault(ImageType.PRIMARY, "") ?: "",
        imageType = ImageType.PRIMARY
    )
}

fun BaseItemDto.asEpisodeMediaSnippet(baseUrl: String, imageType: ImageType): MediaSnippet.Episode {
    return MediaSnippet.Episode(
        id = id.toString(),
        title = seriesName ?: name ?: "",
        state = MediaState(
            isFavorite = userData?.isFavorite ?: false,
            isPlayed = userData?.played ?: false
        ),
        img = when (imageType) {
            ImageType.PRIMARY -> asPrimaryImage(baseUrl)
            ImageType.BACKDROP -> asBackDropImg(baseUrl)
            else -> asPrimaryImage(baseUrl)
        },
        season = parentIndexNumber ?: 0,
        eps = indexNumber ?: 0,
        epsTitle = name ?: ""
    )
}

fun BaseItemDto.asShowMediaSnippet(baseUrl: String, imageType: ImageType): MediaSnippet.Show {
    return MediaSnippet.Show(
        id = id.toString(),
        title = name ?: "",
        series = emptyList(),
        img = when (imageType) {
            ImageType.PRIMARY -> asPrimaryImage(baseUrl)
            ImageType.BACKDROP -> asBackDropImg(baseUrl)
            else -> asPrimaryImage(baseUrl)
        }
    )
}

fun BaseItemDto.asMovieMediaSnippet(baseUrl: String, imageType: ImageType): MediaSnippet.Movie {
    return MediaSnippet.Movie(
        id = id.toString(),
        title = name ?: "",
        state = MediaState(
            isFavorite = userData?.isFavorite ?: false,
            isPlayed = userData?.played ?: false
        ),
        img = when (imageType) {
            ImageType.PRIMARY -> asPrimaryImage(baseUrl)
            ImageType.BACKDROP -> asBackDropImg(baseUrl)
            else -> asPrimaryImage(baseUrl)
        }
    )
}
