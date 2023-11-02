package com.chathil.kotlifin.data.dto.request.media

data class NowWatchingRequest(
    val startIndex: Int,
    val orderBy: OrderBy = OrderBy.DATE_CREATED,
    val sortBy: SortBy = SortBy.DESCENDING,
    val prefetchDistance: Int = 1,
    val limit: Int = 10
) {
    companion object {
        val Initial = NowWatchingRequest(startIndex = 0)
    }
}
