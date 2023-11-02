package com.chathil.kotlifin.data.repository.media

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chathil.kotlifin.data.dto.extension.asNowWatching
import com.chathil.kotlifin.data.dto.request.media.NowWatchingRequest
import com.chathil.kotlifin.data.dto.request.media.SortBy
import com.chathil.kotlifin.data.model.media.NowWatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.itemsApi
import org.jellyfin.sdk.model.api.request.GetItemsRequest
import timber.log.Timber

class NowWatchingPagingSource(
    private val api: () -> Flow<ApiClient>,
    private val request: NowWatchingRequest
) : PagingSource<Int, NowWatching>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowWatching> {
        val currentIndex = params.key ?: 0
        val newRequest = request.copy(startIndex = currentIndex)
        val itemRequest = GetItemsRequest(
            isMovie = true,
            sortOrder = listOf(SortBy.toJellyfinSortOrder(newRequest.sortBy)),
            sortBy = listOf(newRequest.orderBy.rawValue),
            startIndex = newRequest.startIndex,
            limit = newRequest.limit,
        )

        // get the index of the last items and pass it
        return api().map { client ->
            client.itemsApi.getResumeItems(client.userId!!).content to client
        }.map { (pagedResult, client) ->
            val beforeItemCount =
                if (currentIndex > 1) (currentIndex - pagedResult.startIndex) * newRequest.limit else 0
            val nextItemCount =
                if (currentIndex < pagedResult.totalRecordCount) {
                    pagedResult.totalRecordCount - (beforeItemCount + (pagedResult.items?.size
                        ?: 0))
                } else {
                    LoadResult.Page.COUNT_UNDEFINED
                }

            LoadResult.Page(
                data = pagedResult.items?.map { it.asNowWatching(client.baseUrl ?: "") }
                    ?: emptyList(),
                nextKey = if (currentIndex < pagedResult.totalRecordCount) {
                    currentIndex + (pagedResult.items?.size ?: 0)
                } else {
                    null
                },
                prevKey = if (currentIndex > 1) {
                    currentIndex - (pagedResult.items?.size ?: 0)
                } else {
                    null
                },
                itemsBefore = beforeItemCount,
                itemsAfter = nextItemCount
            )
        }.catch<LoadResult<Int, NowWatching>> { error ->
            emit(LoadResult.Error(error.also { Timber.tag("NowWatching").e(error) }))
        }.single()
    }

    override val jumpingSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, NowWatching>): Int? = null
}
