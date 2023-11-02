package com.chathil.kotlifin.data.dto.request.media

import org.jellyfin.sdk.model.api.SortOrder

enum class SortBy {
    ASCENDING, DESCENDING;

    companion object {
        fun toJellyfinSortOrder(sortBy: SortBy): SortOrder {
            return when(sortBy) {
                DESCENDING -> SortOrder.DESCENDING
                ASCENDING -> SortOrder.ASCENDING
            }
        }
    }
}
