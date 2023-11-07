package com.chathil.kotlifin.data.dto.request.media

import org.jellyfin.sdk.model.api.BaseItemKind

enum class MediaType {
    SHOW, MOVIE, EPISODE;

    companion object {
        fun toJellyfinMediaType(type: MediaType): BaseItemKind {
            return when(type) {
                SHOW -> BaseItemKind.SERIES
                MOVIE -> BaseItemKind.MOVIE
                EPISODE -> BaseItemKind.EPISODE
            }
        }
    }
}
