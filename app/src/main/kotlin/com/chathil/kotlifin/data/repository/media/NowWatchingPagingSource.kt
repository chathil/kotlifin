package com.chathil.kotlifin.data.repository.media

import com.chathil.kotlifin.data.api.JellyfinSDKPagingSource
import com.chathil.kotlifin.data.dto.extension.asMediaSnippet
import com.chathil.kotlifin.data.dto.request.media.NowWatchingRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet
import kotlinx.coroutines.flow.Flow
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.Response
import org.jellyfin.sdk.api.client.extensions.itemsApi
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemDtoQueryResult
import org.jellyfin.sdk.model.api.ImageType

class NowWatchingPagingSource(
    api: () -> Flow<ApiClient>,
    private val request: NowWatchingRequest
) : JellyfinSDKPagingSource<MediaSnippet>(api, request) {
    override fun mapResponse(response: BaseItemDto): MediaSnippet {
        return response.asMediaSnippet(baseUrl, ImageType.BACKDROP)
    }

    override suspend fun invokeApiCall(
        api: ApiClient,
        params: LoadParams<Int>
    ): Response<BaseItemDtoQueryResult> {
        return api.userId?.let { uid ->
            val currentIndex = params.key ?: 0
            val itemRequest = request.asGetResumeItemsRequest(uid).copy(startIndex = currentIndex)
            api.itemsApi.getResumeItems(request = itemRequest)
        } ?: throw NullPointerException("Missing uid in the request")
    }
}
