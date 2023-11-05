package com.chathil.kotlifin.data.dto.request.media

import com.chathil.kotlifin.data.dto.request.paged.JellyfinPagedRequest
import org.jellyfin.sdk.model.UUID
import org.jellyfin.sdk.model.api.request.GetResumeItemsRequest

data class NowWatchingRequest(
    override val startIndex: Int,
    override val orderBy: OrderBy = OrderBy.DATE_CREATED,
    override val sortBy: SortBy = SortBy.DESCENDING,
    override val prefetchDistance: Int = 1,
    override val limit: Int = 10
) : JellyfinPagedRequest() {
    companion object {
        val Initial = NowWatchingRequest(startIndex = 0)
    }

    fun asGetResumeItemsRequest(userId: UUID): GetResumeItemsRequest {
        return GetResumeItemsRequest(userId = userId, limit = limit)
    }
}
