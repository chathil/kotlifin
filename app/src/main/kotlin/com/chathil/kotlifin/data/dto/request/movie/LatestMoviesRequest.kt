package com.chathil.kotlifin.data.dto.request.movie

import com.chathil.kotlifin.data.dto.request.media.OrderBy
import com.chathil.kotlifin.data.dto.request.media.SortBy
import com.chathil.kotlifin.data.dto.request.paged.JellyfinPagedRequest

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
}
