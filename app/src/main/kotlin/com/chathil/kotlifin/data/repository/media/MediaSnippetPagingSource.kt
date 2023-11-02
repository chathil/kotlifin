package com.chathil.kotlifin.data.repository.media

import com.chathil.kotlifin.data.api.JellyfinSDKPagingSource
import com.chathil.kotlifin.data.dto.extension.asMediaSnippet
import com.chathil.kotlifin.data.dto.request.movie.LatestMoviesRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet
import kotlinx.coroutines.flow.Flow
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.Response
import org.jellyfin.sdk.api.client.extensions.itemsApi
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemDtoQueryResult

class MediaSnippetPagingSource(
    api: () -> Flow<ApiClient>,
    private val request: LatestMoviesRequest
) : JellyfinSDKPagingSource<MediaSnippet>(api, request) {

    override fun mapResponse(response: BaseItemDto): MediaSnippet {
        return response.asMediaSnippet(baseUrl)
    }

    override suspend fun invokeApiCall(api: ApiClient): Response<BaseItemDtoQueryResult> {
        return api.itemsApi.getItems(request.asGetItemRequest())
    }
}
