package com.chathil.kotlifin.data.dto.request.media

import org.jellyfin.sdk.model.api.BaseItemKind

enum class MediaType {
    TV_SHOW, MOVIE;

    companion object {
        fun toJellyfinMediaType(type: MediaType): BaseItemKind {
            return when(type) {
                TV_SHOW -> BaseItemKind.SERIES
                MOVIE -> BaseItemKind.MOVIE
            }
        }
    }
}