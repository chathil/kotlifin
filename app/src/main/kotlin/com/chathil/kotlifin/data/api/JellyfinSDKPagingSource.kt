package com.chathil.kotlifin.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chathil.kotlifin.data.dto.request.paged.JellyfinPagedRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.Response
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemDtoQueryResult
import javax.net.ssl.HttpsURLConnection

abstract class JellyfinSDKPagingSource<T : Any>(
    private val api: () -> Flow<ApiClient>,
    private val request: JellyfinPagedRequest
) : PagingSource<Int, T>() {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    var baseUrl = ""

    abstract fun mapResponse(response: BaseItemDto): T
    abstract suspend fun invokeApiCall(api: ApiClient): Response<BaseItemDtoQueryResult>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentIndex = params.key ?: 0
        val itemRequest = request.asGetItemRequest().copy(startIndex = currentIndex)

        return api().map { client ->
            baseUrl = client.baseUrl ?: ""
            invokeApiCall(client)
        }.map { response ->
            return@map if (response.status.toString()
                    .startsWith(HttpsURLConnection.HTTP_OK.toString().first()).not()
            ) {
                LoadResult.Error(Throwable("Responded with: ${response.status}"))
            } else {

                val pagedResult = response.content
                val beforeItemCount =
                    if (currentIndex > 1) (currentIndex - pagedResult.startIndex) * (itemRequest.limit
                        ?: DEFAULT_PAGE_SIZE) else 0
                val nextItemCount =
                    if (currentIndex < pagedResult.totalRecordCount) {
                        pagedResult.totalRecordCount - (beforeItemCount + (pagedResult.items?.size
                            ?: 0))
                    } else {
                        LoadResult.Page.COUNT_UNDEFINED
                    }

                LoadResult.Page(
                    data = pagedResult.items?.map(::mapResponse)
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
            }
        }.catch { error ->
            emit(LoadResult.Error(error))
        }.single()
    }

    override val jumpingSupported: Boolean = true
}
