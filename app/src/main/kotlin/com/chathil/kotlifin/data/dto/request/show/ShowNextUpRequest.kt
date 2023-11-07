package com.chathil.kotlifin.data.dto.request.show

import com.chathil.kotlifin.data.dto.request.media.OrderBy
import com.chathil.kotlifin.data.dto.request.media.SortBy
import com.chathil.kotlifin.data.dto.request.paged.JellyfinPagedRequest
import org.jellyfin.sdk.model.UUID
import org.jellyfin.sdk.model.api.request.GetItemsRequest
import org.jellyfin.sdk.model.api.request.GetNextUpRequest
import org.jellyfin.sdk.model.serializer.toUUID

data class ShowNextUpRequest(
    override val startIndex: Int,
    override val orderBy: OrderBy = OrderBy.DATE_CREATED,
    override val sortBy: SortBy = SortBy.DESCENDING,
    override val prefetchDistance: Int = 1,
    override val limit: Int = 10
) : JellyfinPagedRequest() {
    companion object {
        val Initial = ShowNextUpRequest(startIndex = 0)
    }

    fun asShowNextUpRequest(userId: UUID): GetNextUpRequest {
        return GetNextUpRequest(
            userId = userId,
            startIndex = startIndex,
            limit = limit
        )
    }
}
