package com.chathil.kotlifin.data.repository.media

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chathil.kotlifin.data.dto.extension.asMediaSnippet
import com.chathil.kotlifin.data.dto.request.movie.LatestMoviesRequest
import com.chathil.kotlifin.data.dto.request.movie.SortBy
import com.chathil.kotlifin.data.model.media.MediaSnippet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.itemsApi
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.request.GetItemsRequest
import timber.log.Timber

class MediaSnippetPagingSource(
    private val api: () -> Flow<ApiClient>,
    private val request: LatestMoviesRequest
) : PagingSource<Int, MediaSnippet>() {

//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaSnippet> {
//        val currentIndex = params.key ?: 0
//        val newRequest = request.copy(startIndex = currentIndex)
//        val itemRequest = GetItemsRequest(
//            isMovie = true,
//            sortOrder = listOf(SortBy.toJellyfinSortOrder(newRequest.sortBy)),
//            sortBy = listOf(newRequest.orderBy.rawValue),
//            startIndex = newRequest.startIndex,
//            limit = newRequest.limit,
//        )
//
//        // get the index of the last items and pass it
//        return api().map { client ->
//            client.itemsApi.getItems(itemRequest).content
//        }.map { pagedResult ->
//            val beforeItemCount =
//                if (currentIndex > 1) (currentIndex - pagedResult.startIndex) * newRequest.limit else 0
//            val nextItemCount =
//                if (currentIndex < pagedResult.totalRecordCount) {
//                    pagedResult.totalRecordCount - (beforeItemCount + (pagedResult.items?.size
//                        ?: 0))
//                } else {
//                    LoadResult.Page.COUNT_UNDEFINED
//                }
//
//            LoadResult.Page(
////                data = pagedResult.items?.map(BaseItemDto::asMediaSnippet)?.also {
////                    Timber.tag(MediaSnippetPagingSource::class.java.simpleName).d(it.toString())
////                } ?: emptyList(),
//                data = emptyList(),
//                nextKey = if (currentIndex < pagedResult.totalRecordCount) {
//                    currentIndex + (pagedResult.items?.size ?: 0)
//                } else {
//                    null
//                },
//                prevKey = if (currentIndex > 1) {
//                    currentIndex - (pagedResult.items?.size ?: 0)
//                } else {
//                    null
//                },
//                itemsBefore = beforeItemCount,
//                itemsAfter = nextItemCount
//            )
//        }.catch<LoadResult<Int, MediaSnippet>> { error ->  emit(LoadResult.Error(error.also { Timber.tag(MediaSnippetPagingSource::class.java.simpleName).d(error.toString()) })) }.single()
//    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaSnippet> {
        TODO("Not yet implemented")
    }

    override val jumpingSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, MediaSnippet>): Int? = null
}
