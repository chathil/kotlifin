package com.chathil.kotlifin.data.dto.request.movie

data class LatestMoviesRequest(
    val startIndex: Int,
    val orderBy: OrderBy = OrderBy.DATE_CREATED,
    val sortBy: SortBy = SortBy.DESCENDING,
    val prefetchDistance: Int = 1,
    val limit: Int = 10
) {
    companion object {
        val Initial = LatestMoviesRequest(startIndex = 0)
    }
}
