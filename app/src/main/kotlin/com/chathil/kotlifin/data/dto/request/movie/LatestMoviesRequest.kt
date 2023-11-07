package com.chathil.kotlifin.data.dto.request.movie

import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.dto.request.media.OrderBy
import com.chathil.kotlifin.data.dto.request.media.SortBy
import com.chathil.kotlifin.data.dto.request.paged.JellyfinPagedRequest
import org.jellyfin.sdk.model.UUID
import org.jellyfin.sdk.model.api.request.GetItemsRequest

data class LatestMoviesRequest(
    override val startIndex: Int,
    override val orderBy: OrderBy = OrderBy.DATE_CREATED,
    override val sortBy: SortBy = SortBy.DESCENDING,
    override val prefetchDistance: Int = 1,
    override val limit: Int = 10
) : JellyfinPagedRequest() {
    companion object {
        val Initial = LatestMoviesRequest(startIndex = 0)
    }

    fun asGetItemsRequest(): GetItemsRequest {
        return GetItemsRequest(
            sortOrder = listOf(SortBy.toJellyfinSortOrder(sortBy)),
            sortBy = listOf(orderBy.rawValue),
            startIndex = startIndex,
            limit = limit,
            includeItemTypes = supportedMediaTypes.map(MediaType::toJellyfinMediaType)
        )
    }
}
